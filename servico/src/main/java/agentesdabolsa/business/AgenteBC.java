package agentesdabolsa.business;

import java.util.List;

import agentesdabolsa.dao.AcaoDAO;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Action;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;
import agentesdabolsa.trust.ITrust;
import bsh.Interpreter;
import openjade.ontology.Rating;

public class AgenteBC {

	private AcaoDAO agenteDao = AcaoDAO.getInstance();
	private CotacaoDAO cotacaoDao = CotacaoDAO.getInstance();
	private CotacaoDAO ctDao = CotacaoDAO.getInstance();
	private GameBC gameBC = GameBC.getInstance();
	private ConfigBC configBC = ConfigBC.getInstance();
	private ITrust trust = null;

	private static AgenteBC instance = new AgenteBC();

	public static AgenteBC getInstance() {
		return instance;
	}

	public void play(Agente client, int iteration) {
		gameBC.setCurrentIteration(iteration);
		client.getTrust().setIteration(iteration);
		Game game = getGame(client);
		game.setAcao(agenteDao.getRandom(GameBC.acoes));

		List<Cotacao> cotacoes = ctDao.getCotacoes(game, GameBC.random, iteration * configBC.getConfig().getStop());

		Cotacao cotacaoD = cotacaoDao.getCotacao(game.getAcao().getNomeres(), game.getFrom() - configBC.getConfig().getStop());
		game.setCotacao(cotacoes.get(0));

		Interpreter rule = new Interpreter();
		try {
			rule.set("_this", client);
			rule.set("_ahpValue", 1.0);
			rule.set("_game", game);
			rule.set("_acao", game.getAcao());
			rule.set("_carteira", game.getCarteira());
			rule.set("_cotacoes", cotacoes);
			rule.set("_cotacaoD", cotacaoD);
			rule.set("_iteration", iteration);
			rule.set("_advice", null);
			if (configBC.getConfig().getAhp() && client.getRiskAHP() != null && !client.getRiskAHP().isEmpty()) {
				rule.eval(client.getRiskAHP());
				Double _ahpValue = (Double) rule.get("_ahpValue");
				rule.set("_ahpValue", _ahpValue);
			}			
			if (client.getRequestHelp() != null && !client.getRequestHelp().isEmpty()) {
				rule.eval(client.getRequestHelp());
				Agente server = (Agente) rule.get("_request");
				if (server != null) { 
					rule.eval(server.getResponseHelp());
					Action advice = (Action) rule.get("_return");
					if (advice != null) {
						Rating rt = makeRating(client, server, iteration, cotacoes, cotacaoD, advice);
						client.getTrust().addRating(rt);
					}
					rule.set("_advice", advice);
				}
			}
			// joga
			if (client.getActionBefore() != null && !client.getActionBefore().isEmpty()) {
				rule.eval(client.getActionBefore());
				Action action = (Action) rule.get("_return");
				Double value = (Double) rule.get("_value");
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
			if (client.getActionAfter() != null && !client.getActionAfter().isEmpty()) {
				rule.eval(client.getActionAfter());
			}
		} catch (Exception e) {
			Log.error(e);
		}
	}

	private Rating makeRating(Agente client, Agente server, int iteration, List<Cotacao> cotacoes, Cotacao cotacaoD, Action advice) {
		Cotacao cotacao = cotacoes.get(0);
		boolean subiu = (cotacao.compareTo(cotacaoD) <= 0);
		boolean desceu = !subiu;
		boolean acertou = ((subiu && advice.equals(Action.BUY)) || (desceu && advice.equals(Action.SELL)));
		Float valor = Math.abs(cotacaoD.getPreult() - cotacao.getPreult()) / cotacao.getPreult();
		valor = (acertou) ? valor : valor * -1;

		Rating rt = new Rating();
		rt.setClient(client.getAID());
		rt.setServer(server.getAID());
		rt.setRound(iteration);
		rt.setValue(valor.toString());
		return rt;
	}

	public Game getGame(Agente agente) {
		Game game = gameBC.getGame(agente.getAID().getName());
		if (game == null) {
			game = gameBC.newGame(agente.getAID().getName());
		}
		return game;
	}
	
	public ITrust getTrust(){
		return this.trust;
	}
}
