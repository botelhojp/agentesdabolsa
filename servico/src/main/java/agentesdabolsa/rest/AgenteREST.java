package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import io.swagger.annotations.Api;
import javassist.NotFoundException;

@Api(value = "Agente")
@Path("agente")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AgenteREST {

	@GET
	@Path("teste")
	public Response test() throws NotFoundException {
		return Response.ok().entity("tudo ok!").build();
	}

	
}
