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
	private boolean done = false;

	public Time(List<Agente> list, int maxIterations) {
		this.maxIterations = maxIterations;
		this.list = list;
	}

	@Override
	public void run() {
		
		while (!isDone()) {
			iteration++;
			try {
				if (!list.isEmpty()) {
					Log.info( (iteration % 10 == 0) ? "[" + iteration + "]" : ".");
					String trustName = null;
					IMetric metric = gameBC.getMetric().init(iteration);
					for (Iterator<Agente> it = list.iterator(); it.hasNext();) {
						Agente agente = it.next();
						trustName = agente.getTrust().getName();
						metric.beforePlay();
						agenteBC.play(agente, iteration);
						metric.afterPlay();
						if (agente.getName().contains(metric.getAgentPattern())) {
							metric.add(agente);
						}
					}
					GameBC.putResult(trustName, metric.calc(), iteration);
				}
			if (iteration == maxIterations){
				RunnerBC.run_continue();
				Log.info("[done]");
			}
			} catch (Throwable e) {
				Log.error(e);
			}
		}
	}

	private boolean isDone() {
		return (iteration >= maxIterations || done);
	}
	
	public void finish() {
		done = true;
	}

}
