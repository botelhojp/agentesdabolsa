package agentesdabolsa.dao;

import agentesdabolsa.entity.Agente;

public class AgenteDAO extends GenericDAO<Agente> {
	
	private static AgenteDAO dao = new AgenteDAO();
	
	@Override
	protected String getResouce() {
		return "agente";
	}
	
	public static AgenteDAO getInstance() {
		return dao;
	}

	@Override
	protected String getOrder() {
		return "name";
	}
}
