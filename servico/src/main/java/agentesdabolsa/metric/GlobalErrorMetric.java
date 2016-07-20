package agentesdabolsa.metric;

import agentesdabolsa.business.AgenteBC;
import agentesdabolsa.entity.Agente;

public class GlobalErrorMetric extends AbstractMetric  {

	private double count;
	private double error;
	private AgenteBC agenteBC;

	@Override
	public IMetric init(int iteration) {
		if (iteration == 1) {
			count = 0;
			error = 0;
			agenteBC = AgenteBC.getInstance();
		}
		return this;
	}

	@Override
	public void add(Agente agente) {
		if (!agente.getName().contains(agentPattern)) {
			count++;
			boolean acertou = agenteBC.getGame(agente).getResultado();
			if (!acertou) {
				error++;
			}
		}
	}

	@Override
	public double calc() {
		return error / count;
	}

}
