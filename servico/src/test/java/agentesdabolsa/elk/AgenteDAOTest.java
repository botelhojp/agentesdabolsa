package agentesdabolsa.elk;

import org.junit.Assert;
import org.junit.Test;

import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.elk.AgenteELK;

public class AgenteDAOTest {
	
	static{
		ELKDAO.DB = "test";
	}
	
	private AgenteDAO dao = AgenteDAO.getInstance();

	@Test
	public void insert() {
		AgenteELK a1 = dao.insert(new Agente("agente01", 10));
		Assert.assertNull(a1.get_source());
		Agente d = dao.findByID(a1.get_id());
		Assert.assertEquals("agente01", d.getName());
		
		
		d = dao.findByName("agente01");
		Assert.assertEquals("agente01", d.getName());
	}
}
