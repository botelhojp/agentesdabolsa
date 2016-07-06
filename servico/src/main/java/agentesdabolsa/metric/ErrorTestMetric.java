package agentesdabolsa.metric;

import agentesdabolsa.business.AgenteBC;
import agentesdabolsa.config.AppConfig;
import agentesdabolsa.entity.Agente;

public class ErrorTestMetric implements IMetric {

	private int count;
	private int error;
	private AgenteBC agenteBC;

	@Override
	public IMetric init() {
		count = 0;
		error = 0;
		agenteBC = AgenteBC.getInstance();
		return this;
	}

	@Override
	public void add(Agente agente) {
		if (!agente.getName().contains("%")) {
			count++;
			double carteira = agenteBC.getGame(agente).getCarteira();
			double value = (carteira - AppConfig.INITIAL_VALUE) / AppConfig.INITIAL_VALUE;
			if (value < 0){
				error++;
			}
		}
	}

	@Override
	public double calc() {
		return (double) Math.round(error / count * 1000d) / 1000d;
	}



}
