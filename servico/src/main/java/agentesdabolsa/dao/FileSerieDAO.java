package agentesdabolsa.dao;

import agentesdabolsa.entity.Agente;

public class FileSerieDAO extends GenericDAO<Agente> {
	
	private static FileSerieDAO dao = new FileSerieDAO();
	
	@Override
	protected String getResouce() {
		return "file";
	}
	
	public static FileSerieDAO getInstance() {
		return dao;
	}

	@Override
	protected String getOrder() {
		return null;
	}
}
