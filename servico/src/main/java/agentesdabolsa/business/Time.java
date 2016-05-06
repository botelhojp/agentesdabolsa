package agentesdabolsa.business;

import java.util.Iterator;
import java.util.List;

import agentesdabolsa.entity.Agente;

public class Time implements Runnable {
	
	private AgenteBC agenteBC = AgenteBC.getInstance();

	private List<Agente> list;
	private long iteration = 0;
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
				for (Iterator<Agente> it = list.iterator(); it.hasNext();) {
					Agente agente = it.next();
					agenteBC.play(agente, iteration);
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isDone() {
		return ( iteration < maxIterations );
	}

}
