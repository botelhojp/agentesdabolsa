package agentesdabolsa.dao;

import java.text.SimpleDateFormat;
import java.util.List;

import agentesdabolsa.entity.Acao;
import agentesdabolsa.entity.Cotacao;

public class CotacaoDAO extends GenericDAO<Cotacao> {
	private static CotacaoDAO dao = new CotacaoDAO();
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

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
		List<Cotacao> rt = findByAcao(acao.getId());
		return rt;
	}

	private List<Cotacao> findByAcao(long idAcao) {
		String filtro = "{ \"from\" : 0, \"size\" : 600,  \"sort\" : [{ \"datapre\" : \"desc\" }]}";
		String jsonResult = http(getResouce()+ "/_search?q=idAcao:" + idAcao, "POST", filtro);
		return super.list(jsonResult);
	}

}