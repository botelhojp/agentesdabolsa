package agentesdabolsa.dao;

import agentesdabolsa.entity.Cotacao;

public class CotacaoDAO extends GenericDAO<Cotacao>{
	private static CotacaoDAO dao = new CotacaoDAO();

	@Override
	protected String getResouce() {
		return "cotacao";
	}

	public static CotacaoDAO getInstance() {
		return dao;
	}
}