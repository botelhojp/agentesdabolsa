package agentesdabolsa.config;

import java.io.IOException;
import java.util.Properties;

import agentesdabolsa.exception.AppExceptions;

public class AppConfig {
	
	private static AppConfig instance;
	
	private Properties prop;
	
	private AppConfig(){
		try {
			prop = new Properties();
			prop.load(AppConfig.class.getClassLoader().getResourceAsStream("agdb.properties"));
		} catch (IOException e) {
			throw new AppExceptions("Erro ao carregar aquivo de propriedades", e);
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
