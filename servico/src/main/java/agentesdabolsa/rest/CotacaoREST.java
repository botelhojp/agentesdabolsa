package agentesdabolsa.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import agentesdabolsa.commons.AppUtils;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Cotacao;

@Path("cotacoes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class CotacaoREST extends AbstractREST{

	private CotacaoDAO dao = CotacaoDAO.getInstance();

	@GET
	public Response getCotacoes(@QueryParam("acao") String acao) {
		StringBuffer sf = new StringBuffer();

		List<Cotacao> cotacoes = null;
		cotacoes = dao.listCotacoes(acao);

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
		return Response.ok().entity(AppUtils.getMessage("cotacoes", sf.toString())).build();
	}

	@DELETE
	public Response delete() {
		long total = CotacaoDAO.getInstance().deleteAll();
		return Response.ok().entity(AppUtils.getMessage("registros_removidos", "" + total)).build();
	}
}
