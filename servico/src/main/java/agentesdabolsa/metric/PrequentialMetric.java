package agentesdabolsa.metric;

import java.util.ArrayList;
import java.util.List;

import agentesdabolsa.business.AgenteBC;
import agentesdabolsa.entity.Agente;

public class PrequentialMetric extends AbstractMetric {

	private double acertos;
	private double count;
	private double sum;
	private List<Double> fadingFactor;
	private AgenteBC agenteBC;

	@Override
	public IMetric init(int iteration) {
		if (iteration == 1) {
			agenteBC = AgenteBC.getInstance();
			fadingFactor = new ArrayList<Double>();
			sum = 0;
		}
		acertos = 0;
		count = 0;
		return this;
	}

	@Override
	public void add(Agente agente) {
		count++;
		if (agenteBC.getGame(gameBC, agente).getResultado()) {
			acertos++;
		}
	}

	@Override
	public double calc() {
		double avg = acertos / count;
		sum += avg;
		fadingFactor.add(avg);
		if (fadingFactor.size() > super.prequentialFadingFactor) {
			sum -= fadingFactor.remove(0);
		}
		if (fadingFactor.size() == super.prequentialFadingFactor) {
			return sum / super.prequentialFadingFactor;
		} else {
			return 0.0;
		}
	}
}
