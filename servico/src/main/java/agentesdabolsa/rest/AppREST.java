package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import javassist.NotFoundException;

@Path("app")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AppREST {

	@GET
	@Path("version")
	public Response test() throws NotFoundException {
		return Response.ok().entity("1.0.0").build();
	}

	
}
