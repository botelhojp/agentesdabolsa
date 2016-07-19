package agentesdabolsa.business;

import java.util.Iterator;
import java.util.List;

import agentesdabolsa.entity.Agente;
import agentesdabolsa.metric.IMetric;

public class Time implements Runnable {

	private AgenteBC agenteBC = AgenteBC.getInstance();
	private GameBC gameBC = GameBC.getInstance();

	private List<Agente> list;
	private int iteration = 0;
	private long maxIterations = 0;

	public Time(List<Agente> list, int maxIterations) {
		this.maxIterations = maxIterations;
		this.list = list;
	}

	@Override
	public void run() {
		
		while (isDone()) {
			iteration++;
			try {
				if (!list.isEmpty()) {
					Log.info("Iteration >> " + iteration + " <<");
					String trustName = null;
					IMetric metric = gameBC.getMetric().init();
					for (Iterator<Agente> it = list.iterator(); it.hasNext();) {
						Agente agente = it.next();
						trustName = agente.getTrust().getName();
						agenteBC.play(agente, iteration);
						metric.add(agente);
					}
					GameBC.putResult(trustName, metric.calc(), iteration);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isDone() {
		return (iteration < maxIterations);
	}

}
