package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.NotFoundException;

import agentesdabolsa.dao.AcaoDAO;

@Path("acoes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AcaoREST extends AbstractREST {

	private AcaoDAO dao = AcaoDAO.getInstance();

	@GET
	public Response getRandom() throws NotFoundException {
		return Response.ok().entity(dao.listOrderAsc("nomefull")).build();
	}
}
