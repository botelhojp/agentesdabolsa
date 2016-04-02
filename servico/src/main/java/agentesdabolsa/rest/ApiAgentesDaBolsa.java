package agentesdabolsa.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.config.BeanConfig;

@SwaggerDefinition(
		info = @Info(description = "Api agentes da bolsa", title = "Agentes da Bolsa", version = "1.0.0-SNAPSHOT") , 
		consumes = {"application/json" }, 
		produces = { "application/json" }, 
		schemes = { SwaggerDefinition.Scheme.HTTP, SwaggerDefinition.Scheme.HTTPS })
@ApplicationPath("api")
public class ApiAgentesDaBolsa extends Application {

	public ApiAgentesDaBolsa() {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0-SNAPSHOT");
		beanConfig.setBasePath("/agentesdabolsa/api");
		beanConfig.setResourcePackage("agentesdabolsa");
		beanConfig.setScan(true);
	}

}
