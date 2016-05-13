package agentesdabolsa.business;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import agentesdabolsa.dao.AcaoDAO;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Action;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;
import bsh.Interpreter;

public class AgenteBC {

	private AcaoDAO agenteDao = AcaoDAO.getInstance();
	private CotacaoDAO cotacaoDao = CotacaoDAO.getInstance();
	private CotacaoDAO ctDao = CotacaoDAO.getInstance();

	private static AgenteBC instance = new AgenteBC();

	public static AgenteBC getInstance() {
		return instance;
	}

	public void play(Agente agente, long iteration) {
		Game game = getGame(agente);
		game.setAcao(agenteDao.getRandom());
		List<Cotacao> cotacoes = ctDao.findByAcaoRandomResult(game);
		Cotacao cotacaoD = cotacaoDao.getCotacao(game.getAcao().getNomeres(), game.getFrom() - 20);
		game.setCotacao(cotacoes.get(cotacoes.size() - 1));

		Interpreter i = new Interpreter();
		try {
			i.set("_agente", agente);
			i.set("_game", game);
			i.set("_acao", game.getAcao());
			i.set("_carteira", game.getCarteira());
			i.set("_cotacoes", cotacoes);
			i.set("_cotacaoD", cotacaoD);			
			i.set("_iteration", iteration);
			if (agente.getActionBefore() != null && !agente.getActionBefore().isEmpty()) {
				i.eval(agente.getActionBefore());
				Action action = (Action) i.get("_return");
				Double value = (Double) i.get("_value");
				if (action != null && value != null) {
					switch (action) {
					case BUY:
						GameBC.getInstance().buy(game, value, cotacaoD);
						break;
					case SELL:
						GameBC.getInstance().sell(game, value, cotacaoD);
						break;
					case WAIT:
						GameBC.getInstance().wait(game);
						break;
					default:
						break;
					}
				}
			}
			if (agente.getActionAfter() != null && !agente.getActionAfter().isEmpty()) {
				i.eval(agente.getActionAfter());
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			sw.toString();
			Log.info(">>>>>> Error interno <<<<<<<<<\n" + sw.toString());
		}
	}

	private Game getGame(Agente agente) {
		Game game = GameBC.getInstance().getGame(agente.getId() + "");
		if (game == null) {
			game = GameBC.getInstance().newGame(agente.getId() + "");
		}
		return game;
	}

}
