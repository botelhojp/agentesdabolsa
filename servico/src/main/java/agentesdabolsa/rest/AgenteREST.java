package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.NotFoundException;

import agentesdabolsa.dao.AgenteDAO;
import agentesdabolsa.entity.Agente;

@Path("agentes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AgenteREST {
	
	private AgenteDAO dao = AgenteDAO.getInstance();
	
	@GET
	public Response list() throws NotFoundException {
		return Response.ok().entity(dao.list()).build();
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") Long id) throws Exception {
		return Response.ok().entity(dao.findByID(id)).build();
	}

	

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) throws Exception {
		dao.delete(id);
		return Response.ok().build();
	}
	
	
	
    @POST
    public Response insert(final Agente agente) {
    	dao.insert(agente);
        return Response.ok().entity(agente).build();
    }
	
	
	class Body{
		Body(Agente a){
			name = a.getName();
			clone = a.getClones();
		}
		public String name;
		public long clone;
	}

	
}
