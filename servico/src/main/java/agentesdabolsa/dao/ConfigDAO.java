package agentesdabolsa.dao;

import agentesdabolsa.entity.Config;

public class ConfigDAO extends GenericDAO<Config> {
	
	private static ConfigDAO dao = new ConfigDAO();
	
	@Override
	protected String getResouce() {
		return "config";
	}
	
	public static ConfigDAO getInstance() {
		return dao;
	}

	@Override
	protected String getOrder() {
		return "acoes";
	}
}
