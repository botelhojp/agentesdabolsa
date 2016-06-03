package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.NotFoundException;

import agentesdabolsa.dao.AcaoDAO;
import agentesdabolsa.dao.ConfigDAO;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Config;
import agentesdabolsa.exception.AppException;

@Path("configuracoes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ConfigREST {

	private ConfigDAO dao = ConfigDAO.getInstance();

	@GET
	public Response list() throws NotFoundException {
		List<Config> l = dao.list();
		if (!l.isEmpty()){
			return Response.ok().entity(l.get(0)).build();
		}else{
			return Response.ok().build();
		}
		
	}
	
	@GET
	@Path("clean-db")
	public Response cleanDB() throws NotFoundException {
		AcaoDAO.getInstance().deleteAll();
		CotacaoDAO.getInstance().deleteAll();
		return Response.ok().build();
	}

	@POST
	public Response insert(final Config cfg) {
		if (dao.list().isEmpty()) {
			dao.insert(cfg);
		} else {
			update(cfg.getId(), cfg);
		}
		return Response.ok().entity(cfg).build();
	}

	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") Long id, final Config config) {
		if (dao.findByID(id) == null) {
			throw new AppException(Status.BAD_REQUEST, "detalhe", "agente não encontrado");
		}
		config.setId(id);
		dao.update(config);
		return Response.ok().entity(config).build();
	}

}
