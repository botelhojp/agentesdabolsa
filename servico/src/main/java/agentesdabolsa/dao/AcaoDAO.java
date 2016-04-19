package agentesdabolsa.dao;

import agentesdabolsa.entity.Acao;

public class AcaoDAO extends GenericDAO<Acao>{
	private static AcaoDAO dao = new AcaoDAO();

	@Override
	protected String getResouce() {
		return "acao";
	}

	public static AcaoDAO getInstance() {
		return dao;
	}

	public Acao findByName(String nomeres) {
		return findByFieldUniqueResult("nomeres", nomeres);
	}

	public Acao getRandom() {		
		long from = Math.round(count()* Math.random());
		return getEntinty(from,1);
	}
	
	
}
