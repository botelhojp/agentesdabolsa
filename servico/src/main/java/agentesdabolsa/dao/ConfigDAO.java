package agentesdabolsa.dao;

import java.util.List;

import agentesdabolsa.entity.Config;

public class ConfigDAO extends GenericDAO<Config> {
	
	private static ConfigDAO dao = new ConfigDAO();
	
	private static Config bean;
	
	@Override
	protected String getResouce() {
		return "config";
	}
	
	public static ConfigDAO getInstance() {
		return dao;
	}
	
	public Config load(){
		if (bean == null){
			List<Config> l = list();
			if (!l.isEmpty()){
				bean = l.get(0);
			}
		}
		return bean;
	}
	
	@Override
	public long update(Config instance) {
		bean = null;
		return super.update(instance);
	}

	@Override
	protected String getOrder() {
		return null;
	}
}
