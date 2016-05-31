package agentesdabolsa.dao;

import agentesdabolsa.business.GameBC;
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


	public Acao getRandom(String[] acoes) {
		
		long from = (GameBC.random)? Math.round(acoes.length * Math.random()) : 0;
		
		return findByName(acoes[(int) from]);
	}
	
	
}
