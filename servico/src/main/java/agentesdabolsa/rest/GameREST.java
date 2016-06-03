package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.Hashtable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.NotFoundException;

import agentesdabolsa.business.ConfigBC;
import agentesdabolsa.business.GameBC;
import agentesdabolsa.commons.AppUtils;
import agentesdabolsa.dao.AcaoDAO;
import agentesdabolsa.dao.AgenteDAO;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;
import jade.core.AID;

@Path("game")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class GameREST {

	private AcaoDAO dao = AcaoDAO.getInstance();
	private CotacaoDAO ctDao = CotacaoDAO.getInstance();
	private ConfigBC configBC = ConfigBC.getInstance();

	@GET
	@Path("start")
	public Response start(@QueryParam("user") String user) throws NotFoundException {
		return Response.ok().entity(GameBC.getInstance().newGame(user)).build();
	}

	@GET
	@Path("play")
	public Response getRandom(@QueryParam("game") String user, @QueryParam("iteration") Integer iteration) throws NotFoundException {
		
		Game game = GameBC.getInstance().getGame(user);
		game.setIteration(iteration);
		game.setAcao(dao.getRandom(GameBC.acoes));
		StringBuffer sf = new StringBuffer();
		List<Cotacao> cotacoes = ctDao.findByAcaoRandomResult(game, GameBC.random, iteration);
		
		game.setCotacao(cotacoes.get(0));
		
		for (int i = 0; i < cotacoes.size(); i++) {
			Cotacao cotacao = cotacoes.get(i);
			sf.append(cotacao.getDatapre()).append(",");
			sf.append(cotacao.getPreabe()).append(",");
			sf.append(cotacao.getPremax()).append(",");
			sf.append(cotacao.getPremin()).append(",");
			sf.append(cotacao.getPreult());
			if (i < cotacoes.size() - 1) {
				sf.append("\n");
			}
		}
		Hashtable<String, Object> rt = new Hashtable<String, Object>();
		rt.put("acao", game.getAcao());
		rt.put("cotecoes", sf.toString());
		return Response.ok().entity(rt).build();
	}
	
	
	@GET
	@Path("buy")
	public Response buy(@QueryParam("game") String user) throws NotFoundException {
		Game game = GameBC.getInstance().getGame(user);
		GameBC.getInstance().buy(game);
		return Response.ok().entity(game).build();
	}
	
	
	@GET
	@Path("sell")
	public Response sell(@QueryParam("game") String user) throws NotFoundException {
		Game game = GameBC.getInstance().getGame(user);
		GameBC.getInstance().sell(game);
		return Response.ok().entity(game).build();
	}
	
	
	@GET
	@Path("simulate_start")
	public Response add(@QueryParam("rounds") int rounds ) throws NotFoundException {
		AgenteDAO agenteDao = AgenteDAO.getInstance();
		GameBC.configure(rounds);
		GameBC game = GameBC.getInstance();
		List<Agente> l = agenteDao.list();
		for(Agente agente : l){
			if (agente.getEnabled() && agente.getClones() != null && agente.getClones() > 0){
				for (int i = 0; i < agente.getClones(); i++) {
					Agente clone = (Agente) AppUtils.cloneObject(agente);
					clone.setAID(new AID(clone.getName()+ "_" +i, true));
					game.add(clone);
				}
			}
		}
		GameBC.start();
		return Response.ok().build();
	}
	
	
}
