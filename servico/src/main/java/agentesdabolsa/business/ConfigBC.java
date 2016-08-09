package agentesdabolsa.business;

import agentesdabolsa.dao.ConfigDAO;
import agentesdabolsa.entity.Config;

public class ConfigBC {
	private static ConfigBC instance;

	private ConfigDAO configDao;
	private String[] acoes;

	private ConfigBC() {
		configDao = ConfigDAO.getInstance();
		acoes = getConfig().getAcoes().trim().split(" ");
	}

	public static ConfigBC getInstance() {
		if (instance == null) {
			instance = new ConfigBC();
		}
		return instance;
	}

	public Config getConfig() {
		return configDao.load();
	}

	public String[] getAcoes() {
		return acoes;
	}
}