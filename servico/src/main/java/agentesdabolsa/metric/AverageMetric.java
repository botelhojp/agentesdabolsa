package agentesdabolsa.metric;

import agentesdabolsa.business.AgenteBC;
import agentesdabolsa.config.AppConfig;
import agentesdabolsa.entity.Agente;

public class AverageMetric extends AbstractMetric {

	private double count;
	private double sum;
	private AgenteBC agenteBC;

	@Override
	public IMetric init(int iteration) {
		count = 0;
		sum = 0.0;
		agenteBC = AgenteBC.getInstance();
		return this;
	}

	@Override
	public void add(Agente agente) {
		if (!agente.getName().contains(agentPattern)) {
			count++;
			sum += agenteBC.getGame(agente).getCarteira() - AppConfig.INITIAL_VALUE;
		}
	}

	@Override
	public double calc() {
		return sum / count;
	}

}
