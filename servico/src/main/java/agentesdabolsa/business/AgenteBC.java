package agentesdabolsa.business;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import agentesdabolsa.commons.AppUtils;
import agentesdabolsa.dao.AcaoDAO;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Action;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;
import bsh.Interpreter;

public class AgenteBC {

	private AcaoDAO agenteDao = AcaoDAO.getInstance();
	private CotacaoDAO ctDao = CotacaoDAO.getInstance();

	private static AgenteBC instance = new AgenteBC();

	public static AgenteBC getInstance() {
		return instance;
	}

	public void play(Agente agente, long iteration) {
		Game game = getGame(agente);
		game.setAcao(agenteDao.getRandom());
		List<Cotacao> cotacoes = ctDao.findByAcaoRandomResult(game);
		game.setCotacao(cotacoes.get(0));

		Interpreter i = new Interpreter();
		try {
			i.set("_acao", game.getAcao());
			i.set("_carteira", game.getCarteira());
			i.set("_cotacoes", cotacoes);
			i.eval(agente.getActionBefore());
			Action action =  (Action) i.get("_return");
			Double value =  (Double) i.get("_value");
			switch (action) {
			case BUY:
				GameBC.getInstance().buy(game, value);
				break;
			case SELL:
				GameBC.getInstance().sell(game, value);
				break;
			case WAIT:
				GameBC.getInstance().wait(game);
				break;
			default:
				break;
			}
			
			LogBC.log(iteration + ": Agente: (" + agente.getName() + ") Carteira: " + AppUtils.formatMoeda(game.getCarteira()) ) ;
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			sw.toString();
			LogBC.log("Error: " + sw.toString());
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
