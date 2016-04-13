package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import agentesdabolsa.commons.AppUtils;

@Path("cotacoes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class ContacaoREST {
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@GET
	public Response getCotacoes(@QueryParam("acao") String acao, @QueryParam("init") String init, @QueryParam("end") String end){
		StringBuffer sf = new StringBuffer();
		System.out.println(acao);
		try {
			Date initDt = df.parse(init);
			Date endDt = df.parse(end);
			while (endDt.compareTo(initDt) >= 0){
				sf.append(df.format(endDt)).append(",");
				sf.append("180.19,181.92,178.55,180.94");
				Calendar c = GregorianCalendar.getInstance();
				c.setTime(endDt);
				c.add(Calendar.DAY_OF_MONTH, -1);	
				endDt = c.getTime();
				if (endDt.compareTo(initDt) >= 0){
					sf.append("\n");
				}
			}
			return Response.ok().entity(AppUtils.getMessage("cotacoes", sf.toString())).build();
		} catch (ParseException e) {
			return Response.status(422).build();
		}
	}

}
