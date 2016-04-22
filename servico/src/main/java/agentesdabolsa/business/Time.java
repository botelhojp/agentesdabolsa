package agentesdabolsa.business;

import java.util.Iterator;
import java.util.List;

import agentesdabolsa.entity.Agente;

public class Time implements Runnable {
	
	private AgenteBC agenteBC = AgenteBC.getInstance();

	private List<Agente> list;
	private long iteration = 0;
	
	public Time(List<Agente> list) {
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
					LogBC.log("agente:" + agente.getId());
				}
				LogBC.log("sleep...");
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isDone() {
		return true;
	}

}
