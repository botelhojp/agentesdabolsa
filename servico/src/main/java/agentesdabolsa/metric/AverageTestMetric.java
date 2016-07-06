package agentesdabolsa.metric;

import agentesdabolsa.business.AgenteBC;
import agentesdabolsa.config.AppConfig;
import agentesdabolsa.entity.Agente;

public class AverageTestMetric implements IMetric {

	private int count;
	private double sum;
	private AgenteBC agenteBC;

	@Override
	public IMetric init() {
		count = 0;
		sum = 0.0;
		agenteBC = AgenteBC.getInstance();
		return this;
	}

	@Override
	public void add(Agente agente) {
		if (!agente.getName().contains("%")) {
			count++;
			double carteira = agenteBC.getGame(agente).getCarteira();
			double value = (carteira - AppConfig.INITIAL_VALUE) / AppConfig.INITIAL_VALUE;
			sum += value;
		}
	}

	@Override
	public double calc() {
		return (double) Math.round(sum / count * 1000d) / 1000d;
	}

}
