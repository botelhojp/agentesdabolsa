package agentesdabolsa.business;

import java.util.List;

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
		List<Config> l = configDao.list();
		if (!l.isEmpty()){
			return l.get(0);
		}
		return null;
	}
}
