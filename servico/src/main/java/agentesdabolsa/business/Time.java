package agentesdabolsa.business;

import java.util.Iterator;
import java.util.List;

import agentesdabolsa.config.AppConfig;
import agentesdabolsa.entity.Agente;

public class Time implements Runnable {

	private AgenteBC agenteBC = AgenteBC.getInstance();

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
					String trustName = null;
					double value = 0.0;
					for (Iterator<Agente> it = list.iterator(); it.hasNext();) {
						Agente agente = it.next();
						trustName = agente.getTrust().getClass().getSimpleName();
						agenteBC.play(agente, iteration);
						value += (agenteBC.getGame(agente).getCarteira() - AppConfig.INITIAL_VALUE) / AppConfig.INITIAL_VALUE;
					}
					value = value / list.size();
					GameBC.putResult(trustName, value, iteration);
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
