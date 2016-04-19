package agentesdabolsa.dao;

import java.util.Hashtable;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;

import agentesdabolsa.entity.Acao;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.exception.AppException;

public class CotacaoDAO extends GenericDAO<Cotacao> {
	
	private static long SIZE = 100;
	private static CotacaoDAO dao = new CotacaoDAO();
	private static Hashtable<Long, Long> cacheTotal = new Hashtable<Long, Long>();

	private AcaoDAO acaoDao = AcaoDAO.getInstance();

	@Override
	protected String getResouce() {
		return "cotacao";
	}

	public static CotacaoDAO getInstance() {
		return dao;
	}

	public List<Cotacao> listCotacoes(String _acao) {
		Acao acao = acaoDao.findByName(_acao);
		if (acao == null) {
			throw new AppException(Status.NOT_FOUND, "detalhe", "A��o [" + _acao + "] n�o encontrada");
		}
		List<Cotacao> rt = findByAcao(acao.getId());
		return rt;
	}

	private List<Cotacao> findByAcao(long idAcao) {
		String filtro = "{ \"from\" : 0, \"size\" : 600,  \"sort\" : [{ \"datapre\" : \"desc\" }]}";
		String jsonResult = http(getResouce() + "/_search?q=idAcao:" + idAcao, "POST", filtro);
		return super.list(jsonResult);
	}

	public List<Cotacao> findByAcaoRandomResult(String _acao) {
		Acao acao = acaoDao.findByName(_acao);
		if (acao == null) {
			throw new AppException(Status.NOT_FOUND, "detalhe", "A��o [" + _acao + "] n�o encontrada");
		}
		
		long total = 0;
		if (cacheTotal.containsKey(acao.getId())) {
			total = cacheTotal.get(acao.getId());
		} else {
			try {
				String filtro = "{ \"from\" : 0, \"size\" : 1,  \"sort\" : [{ \"datapre\" : \"desc\" }]}";
				String jsonResult = http(getResouce() + "/_search?q=idAcao:" + acao.getId(), "POST", filtro);
				JSONArray jsonArray = new JSONArray("[" + jsonResult + "]");
				total = jsonArray.getJSONObject(0).getJSONObject("hits").getLong("total");
				cacheTotal.put(acao.getId(), total);
			} catch (Exception e) {
				throw new AppException("erro no parse json", e);
			}
		}
		long from = Math.round((total - SIZE) * Math.random());

		String filtro = "{ \"from\" : " + from + ", \"size\" : " + SIZE + ",  \"sort\" : [{ \"datapre\" : \"desc\" }]}";
		String jsonResult = http(getResouce() + "/_search?q=idAcao:" + acao.getId(), "POST", filtro);
		return super.list(jsonResult);
	}

}