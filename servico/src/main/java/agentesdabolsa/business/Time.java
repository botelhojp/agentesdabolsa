package agentesdabolsa.business;

import java.util.Iterator;
import java.util.List;

import agentesdabolsa.entity.Agente;
import agentesdabolsa.metric.IMetric;

public class Time implements Runnable {

	private AgenteBC agenteBC = AgenteBC.getInstance();
	private int iteration = 0;
	private long maxIterations = 0;
	private boolean done = false;
	private GameBC gameBC;

	public Time(int maxIterations, GameBC _gameBC) {
		gameBC = _gameBC;
		this.maxIterations = maxIterations;
	}

	@Override
	public void run() {
		List<Agente> list = gameBC.getAgents();
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
						agenteBC.play(gameBC.getRunnerId(), agente, iteration);
						metric.afterPlay();
						if (agente.getName().contains(metric.getAgentPattern())) {
							metric.add(agente);
						}
					}
					ResultBC.putResult(trustName, metric.calc(), iteration);
				}
			} catch (Throwable e) {
				done = true;
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
