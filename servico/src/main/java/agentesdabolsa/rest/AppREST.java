package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.NotFoundException;

import agentesdabolsa.dao.AgenteDAO;

@Path("app")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class AppREST extends AbstractREST{
	
	private AgenteDAO dao = AgenteDAO.getInstance();

	@GET
	@Path("version")
	public Response test() throws NotFoundException {
		Version version = new Version("1.0.0", dao.getVersion());
		return Response.ok().entity(version).build();
	}
	
	 class Version implements Serializable{
		private static final long serialVersionUID = 1L;
		String versionApp;
		String versionELK;
		public Version(String versionApp, String versionELK) {
			super();
			this.versionApp = versionApp;
			this.versionELK = versionELK;
		}
		public String getVersionApp() {
			return versionApp;
		}
		public void setVersionApp(String versionApp) {
			this.versionApp = versionApp;
		}
		public String getVersionELK() {
			return versionELK;
		}
		public void setVersionELK(String versionELK) {
			this.versionELK = versionELK;
		}
	}

	
}
