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
		agenteBC.setResult("");
		String csv =  "iteration;value\n";
		while (isDone()) {
			iteration++;
			try {
				double value = 0.0;
				for (Iterator<Agente> it = list.iterator(); it.hasNext();) {
					Agente agente = it.next();
					agenteBC.play(agente, iteration);
					value += (agenteBC.getGame(agente).getCarteira() - AppConfig.INITIAL_VALUE) / AppConfig.INITIAL_VALUE;
				}
				value = value /  list.size();
				csv += iteration + ";" + value + "\n";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		agenteBC.setResult(csv);
	}

	private boolean isDone() {
		return ( iteration < maxIterations );
	}

}
