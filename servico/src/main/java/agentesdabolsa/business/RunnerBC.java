package agentesdabolsa.business;

import java.util.List;

import agentesdabolsa.commons.AppUtils;
import agentesdabolsa.dao.AgenteDAO;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.metric.IMetric;
import agentesdabolsa.trust.CentralModel;
import agentesdabolsa.trust.FIREModel;
import agentesdabolsa.trust.ICEModel;
import agentesdabolsa.trust.ITrust;
import agentesdabolsa.trust.LessModel;
import agentesdabolsa.trust.MARSHModel;
import agentesdabolsa.trust.TRAVOSModel;
import jade.core.AID;

@SuppressWarnings("rawtypes")
public class RunnerBC {

	private static ConfigBC configBC = ConfigBC.getInstance();
	private static Class[] classes = { LessModel.class, MARSHModel.class, TRAVOSModel.class, FIREModel.class, ICEModel.class, CentralModel.class };
	private static int index = 0;

	private static String trustClassName;
	private static String metricClassName;
	private static int rounds;
	private static boolean repet;

	@SuppressWarnings("unchecked")
	public static void run(String _trustClassName, String _metricClassName, int _rounds, boolean _repet)
			throws Exception {
		trustClassName = _trustClassName;
		metricClassName = _metricClassName;
		rounds = _rounds;
		repet = _repet;
		if (repet && index >= 0) {
			_trustClassName = classes[index++].getCanonicalName();
			trustClassName = _trustClassName;
			if (index >= classes.length) {
				index = 0;
				repet = false;
			}
		}
		Log.info("start\n");
		System.gc();
		Random.initSeed(configBC.getConfig().getRandomSeed());
		Class<ITrust> trustClazz = (Class<ITrust>) Class.forName(_trustClassName);
		Class<IMetric> metricClazz = (Class<IMetric>) Class.forName(_metricClassName);
		AgenteDAO agenteDao = AgenteDAO.getInstance();
		IMetric metric = metricClazz.newInstance();
		GameBC.configure(_rounds);
		GameBC gameBC = GameBC.getInstance();
		gameBC.setMetric(metric);
		List<Agente> l = agenteDao.list();
		for (Agente agente : l) {
			if (agente.getEnabled() && agente.getClones() != null && agente.getClones() > 0) {
				for (int i = 0; i < agente.getClones(); i++) {
					Agente clone = (Agente) AppUtils.cloneObject(agente);
					clone.setAID(new AID(clone.getName() + "_" + i, true));
					ITrust instance = trustClazz.newInstance();
					instance.setAgent(clone);
					clone.setTrust(instance);
					gameBC.add(clone);
				}
			}
		}
		GameBC.start();
	}

	public static void run_continue() throws Exception {
		if (repet) {
			run(trustClassName, metricClassName, rounds, repet);
		}
	}

}
