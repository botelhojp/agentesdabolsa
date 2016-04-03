package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import agentesdabolsa.entity.Agente;
import javassist.NotFoundException;

@Path("agente")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AgenteREST {

	@GET
	public Response list() throws NotFoundException {
		List<Agente> list = new ArrayList<>();
		for(int i = 0; i < 200; i++){
			list.add(new Agente("agente_" + i, 10));
		}
		return Response.ok().entity(list).build();
	}

	
}
