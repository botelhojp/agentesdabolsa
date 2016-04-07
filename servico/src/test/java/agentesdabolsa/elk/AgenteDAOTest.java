package agentesdabolsa.elk;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import agentesdabolsa.dao.AgenteDAO;
import agentesdabolsa.dao.ELKDAO;
import agentesdabolsa.entity.Agente;

public class AgenteDAOTest {
	
	static{
		ELKDAO.DB = "test";
	}
	
	private AgenteDAO dao = AgenteDAO.getInstance();

	@Test
	public void insert() {
		long id = dao.insert(new Agente("agente01", 10));
		Assert.assertTrue(id > 0);
	}
	
	@Test
	public void findByID() {
		long id = dao.insert(new Agente("agente01_findByID", 10));
		Agente d = dao.findByID(id);
		Assert.assertEquals("agente01_findByID", d.getName());
	}
	
	@Test
	public void findByField() {
		dao.insert(new Agente("agente01_findByID", 10));
		List<Agente> l = dao.findByField("name", "agente01_findByID");
		Assert.assertEquals("agente01_findByID", l.get(0).getName());
	}
	
	@Test
	public void update() {
		long id = dao.insert(new Agente("agente01_update", 10));
		Agente d = dao.findByID(id);
		d.setName("agente01_update_alterado");
		long idu = dao.update(d);
		Assert.assertEquals(id, idu);
		Assert.assertEquals("agente01_update_alterado", dao.findByID(id).getName());
	}
	
	@Test
	public void delete() {
		long id = dao.insert(new Agente("agente01_delete", 10));
		long idd = dao.delete(id);
		Assert.assertEquals(id, idd);
		Assert.assertNull(dao.findByID(id));
	}
}
