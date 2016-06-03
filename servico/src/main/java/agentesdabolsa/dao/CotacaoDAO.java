package agentesdabolsa.dao;

import java.util.Hashtable;
import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;

import agentesdabolsa.entity.Acao;
import agentesdabolsa.entity.Cotacao;
import agentesdabolsa.entity.Game;
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
			throw new AppException(Status.NOT_FOUND, "detalhe", "Ação [" + _acao + "] não encontrada");
		}
		List<Cotacao> rt = findByAcao(acao.getId());
		return rt;
	}

	private List<Cotacao> findByAcao(long idAcao) {
		String filtro = "{ \"from\" : 0, \"size\" : 2000,  \"sort\" : [{ \"datapre\" : \"desc\" }]}";
		String jsonResult = http(getResouce() + "/_search?q=idAcao:" + idAcao, "POST", filtro);
		return super.list(jsonResult);
	}

	public List<Cotacao> findByAcaoRandomResult(Game game, Boolean random, int iteration) {
		Acao acao = acaoDao.findByName(game.getAcao().getNomeres());
		if (acao == null) {
			throw new AppException(Status.NOT_FOUND, "detalhe", "Ação [" + game.getAcao().getNomeres() + "] não encontrada");
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
		
		//pega o primeiro
		long from = (total - SIZE) - iteration;
		
		//pega aleatorio
		if (random){
			from = Math.round((total - SIZE) * Math.random());
		}
		
		game.setFrom(from);

		String filtro = "{ \"from\" : " + from + ", \"size\" : " + SIZE + ",  \"sort\" : [{ \"datapre\" : \"desc\" }]}";
		String jsonResult = http(getResouce() + "/_search?q=idAcao:" + acao.getId(), "POST", filtro);
		
		List<Cotacao> r = super.list(jsonResult);
		removeDuplicates(r);
		
		return r;
	}

	private void removeDuplicates(List<Cotacao> r) {
		for (int i = 0; i < r.size(); i++) {
			int next = i + 1;
			if (next >= r.size()){
				break;
			}
			Cotacao c = r.get(i);
			Cotacao k = r.get(next);
			if (c.getDatapre().equals(k.getDatapre())){
				delete(k.getId());
				r.remove(next);
				i--;
			}
		}
		
	}

	public Cotacao getCotacao(String _acao, long from) {
		Acao acao = acaoDao.findByName(_acao);
		if (acao == null) {
			throw new AppException(Status.NOT_FOUND, "detalhe", "Ação [" + _acao + "] não encontrada");
		}
		from = (from < 0)? 0 : from;
		String filtro = "{ \"from\" : " + from + ", \"size\" : " + SIZE + ",  \"sort\" : [{ \"datapre\" : \"desc\" }]}";
		String jsonResult = http(getResouce() + "/_search?q=idAcao:" + acao.getId(), "POST", filtro);
		return super.list(jsonResult).get(0);		
	}

}