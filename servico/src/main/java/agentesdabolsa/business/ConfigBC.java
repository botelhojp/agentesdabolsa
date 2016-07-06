package agentesdabolsa.business;

import agentesdabolsa.dao.ConfigDAO;
import agentesdabolsa.entity.Config;

public class ConfigBC {
	private static ConfigBC instance;
	
	private ConfigDAO configDao;
	
	private ConfigBC(){
		configDao = ConfigDAO.getInstance();
	}
	
	public static ConfigBC getInstance() {
		if (instance == null){
			instance = new ConfigBC();
		}
		return instance;
	}
	
	public Config getConfig(){
		return configDao.load();
	}
}
