package agentesdabolsa.config;

import java.io.IOException;
import java.util.Properties;

import agentesdabolsa.exception.AppException;

public class AppConfig {
	
	private static AppConfig instance;
	
	public static int INITIAL_VALUE = 100000;
	
	private Properties prop;
	
	private AppConfig(){
		try {
			prop = new Properties();
			prop.load(AppConfig.class.getClassLoader().getResourceAsStream("agdb.properties"));
		} catch (IOException e) {
			throw new AppException("Erro ao carregar aquivo de propriedades", e);
		}
	}
	
	public static AppConfig getInstance(){
		if (instance == null ){
			instance = new AppConfig();
		}
		return instance;
	}

	public String getELKServer() {
		return prop.get("agdb.elk.server").toString();
	}

}
