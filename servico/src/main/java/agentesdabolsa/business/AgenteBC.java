package agentesdabolsa.business;

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
			i.set("_cotacoes", cotacoes);
			i.eval(agente.getActionBefore());
			Action action =  (Action) i.get("_return");
			switch (action) {
			case BUY:
				GameBC.getInstance().buy(game);
				break;
			case SELL:
				GameBC.getInstance().sell(game);
				break;
			default:
				break;
			}
			
			LogBC.log(iteration + ": Agente: (" + agente.getName() + ") Carteira: " + AppUtils.formatMoeda(game.getCarteira()) ) ;
		} catch (Exception e) {
			e.printStackTrace();
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
