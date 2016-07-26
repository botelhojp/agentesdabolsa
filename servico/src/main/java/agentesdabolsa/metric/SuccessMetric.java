package agentesdabolsa.metric;

import agentesdabolsa.business.AgenteBC;
import agentesdabolsa.entity.Agente;

public class SuccessMetric extends AbstractMetric {

	private double count;
	private double hits;
	private AgenteBC agenteBC;

	@Override
	public IMetric init(int iteration) {
		if (iteration == 1) {
			count = 0;
			hits = 0;
			agenteBC = AgenteBC.getInstance();
		}
		return this;
	}

	@Override
	public void add(Agente agente) {
		count++;
		boolean acertou = agenteBC.getGame(agente).getResultado();
		if (acertou) {
			hits++;
		}
	}

	@Override
	public double calc() {
		return hits / count;
	}

}
