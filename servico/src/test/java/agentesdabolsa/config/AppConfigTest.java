package agentesdabolsa.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppConfigTest {

	@Test
	public void test() {
		assertEquals("http://localhost:9200", AppConfig.getInstance().getELKServer());
	}

}
