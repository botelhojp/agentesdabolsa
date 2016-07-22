package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sun.jersey.api.NotFoundException;

import agentesdabolsa.dao.AgenteDAO;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.exception.AppException;

@Path("agentes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AgenteREST extends AbstractREST{

	private AgenteDAO dao = AgenteDAO.getInstance();

	@GET
	public Response list() throws NotFoundException {
		return Response.ok().entity(dao.list()).build();
	}

	@GET
	@Path("{id}")
	public Response get(@PathParam("id") Long id) throws Exception {
		Agente bean = dao.findByID(id);
		if (bean == null) {
			throw new AppException(Status.NOT_FOUND, "detalhe", "agente n�o encontrado");
		}
		return Response.ok().entity(dao.findByID(id)).build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) throws Exception {
		Agente bean = dao.findByID(id);
		if (bean == null) {
			throw new AppException(Status.NOT_FOUND, "detalhe", "agente n�o encontrado");
		}
		dao.delete(id);
		return Response.ok().build();
	}

	@POST
	public Response insert(final Agente agente) {
		if (agente.getName()== null){
			throw new AppException(Status.BAD_REQUEST, "detalhe", "agente n�o possui nome");
		}
		dao.insert(agente);
		return Response.ok().entity(agente).build();
	}
	
	@PUT
	@Path("{id}")
	public Response update(@PathParam("id") Long id, final Agente agente) {
		if (dao.findByID(id) == null) {
			throw new AppException(Status.BAD_REQUEST, "detalhe", "agente n�o encontrado");
		}
		if (agente.getName()== null){
			throw new AppException(Status.BAD_REQUEST, "detalhe", "agente n�o possui nome");
		}
		agente.setId(id);
		dao.update(agente);
		return Response.ok().entity(agente).build();
	}

	class Body {
		Body(Agente a) {
			name = a.getName();
			clone = a.getClones();
		}

		public String name;
		public long clone;
	}
}
