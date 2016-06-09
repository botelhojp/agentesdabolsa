package agentesdabolsa.business;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.List;

import agentesdabolsa.dao.AcaoDAO;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Action;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;
import bsh.Interpreter;
import jade.core.AID;
import openjade.ontology.Rating;

public class AgenteBC {

	private AcaoDAO agenteDao = AcaoDAO.getInstance();
	private CotacaoDAO cotacaoDao = CotacaoDAO.getInstance();
	private CotacaoDAO ctDao = CotacaoDAO.getInstance();
	private GameBC gameBC = GameBC.getInstance();
	private ConfigBC configBC = ConfigBC.getInstance();


	private static AgenteBC instance = new AgenteBC();

	public static AgenteBC getInstance() {
		return instance;
	}

	public void play(Agente client, int iteration) {
		//List<Advice> advices = new ArrayList<Advice>();
		Game game = getGame(client);
		game.setAcao(agenteDao.getRandom(GameBC.acoes));
		
		List<Cotacao> cotacoes = ctDao.getCotacoes(game, GameBC.random, iteration * configBC.getConfig().getStop());
		
		
		Cotacao cotacaoD = cotacaoDao.getCotacao(game.getAcao().getNomeres(), game.getFrom() - configBC.getConfig().getStop());
		game.setCotacao(cotacoes.get(0));

		Interpreter i = new Interpreter();
		try {
			i.set("_agente", client);
			i.set("_game", game);
			i.set("_acao", game.getAcao());
			i.set("_carteira", game.getCarteira());
			i.set("_cotacoes", cotacoes);
			i.set("_cotacaoD", cotacaoD);			
			i.set("_iteration", iteration);
//			i.set("_trust", game.getTrust());
			//pedir ajuda			
			if (client.getRequestHelp() != null && !client.getRequestHelp().isEmpty()) {
				i.eval(client.getRequestHelp());
				Action action = (Action) i.get("_request");
				if (action != null && action.equals(Action.REQUEST_ALL)){
					Hashtable<AID, Action> advices = new Hashtable<AID, Action>();
					
					for (Agente server : GameBC.getAgents()){
						if (!server.getAID().equals(client.getAID()) && server.getResponseHelp() != null && !server.getResponseHelp().isEmpty()){
							i.eval(server.getResponseHelp());
							Action advice = (Action) i.get("_return");
							if (advice != null){
								Rating rt = makeRating(client, server, iteration, cotacoes, cotacaoD, advice);
								client.getTrust().addRating(rt);
								advices.put(server.getAID(), advice);
							}
						}
					}
					AID best = client.getTrust().getBest();
					i.set("_advice", advices.get(best));
				}
			}
			
			//joga
			if (client.getActionBefore() != null && !client.getActionBefore().isEmpty()) {
				i.eval(client.getActionBefore());
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
			if (client.getActionAfter() != null && !client.getActionAfter().isEmpty()) {
				i.eval(client.getActionAfter());
			}
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			sw.toString();
			Log.info(">>>>>> Error interno <<<<<<<<<\n" + sw.toString());
		}
	}

	private Rating makeRating(Agente client, Agente server, int iteration, List<Cotacao> cotacoes, Cotacao cotacaoD, Action advice) {
		Cotacao cotacao = cotacoes.get(0);		
		boolean subiu = (cotacao.compareTo(cotacaoD) <= 0);
		boolean desceu = !subiu;		
		boolean acertou =  ( (subiu && advice.equals(Action.BUY)) || (desceu && advice.equals(Action.SELL)) );
		Float valor = Math.abs((cotacaoD.getPreult() - cotacao.getPreult()) / cotacao.getPreult());
		valor = (acertou) ? valor : valor * - 1;
		
		Rating rt = new Rating();
		rt.setClient(client.getAID());
		rt.setServer(server.getAID());
		rt.setRound(iteration);
		rt.setValue(valor.toString());
		return rt;
	}

	private Game getGame(Agente agente) {
		Game game = gameBC.getGame(agente.getAID().getLocalName());
		if (game == null) {
			game = gameBC.newGame(agente.getAID().getLocalName());
		}
		return game;
	}

}
