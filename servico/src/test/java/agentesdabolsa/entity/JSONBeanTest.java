package agentesdabolsa.entity;

import org.junit.Assert;
import org.junit.Test;

public class JSONBeanTest {

	@Test
	public void test() {
		Agente a = new Agente();
		a.setName("agent009");
		Assert.assertEquals("{\"name\":\"agent009\",\"id\":0}", a.toString());
	}

}
