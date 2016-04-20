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

import agentesdabolsa.business.GameBC;
import agentesdabolsa.commons.AppUtils;
import agentesdabolsa.dao.AcaoDAO;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;

@Path("game")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class GameREST {

	private AcaoDAO dao = AcaoDAO.getInstance();
	private CotacaoDAO ctDao = CotacaoDAO.getInstance();

	@GET
	@Path("start")
	public Response start(@QueryParam("user") String user) throws NotFoundException {
		return Response.ok().entity(GameBC.getInstance().newGame(user)).build();
	}

	@GET
	@Path("play")
	public Response getRandom(@QueryParam("game") String user) throws NotFoundException {
		Game game = GameBC.getInstance().getGame(user);
		game.setAcao(dao.getRandom());
		StringBuffer sf = new StringBuffer();
		List<Cotacao> cotacoes = ctDao.findByAcaoRandomResult(game);
		
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
		GameBC.getInstance().buy(game);
		return Response.ok().entity(game).build();
	}
}
