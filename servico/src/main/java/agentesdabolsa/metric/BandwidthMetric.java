package agentesdabolsa.metric;

import agentesdabolsa.commons.AppUtils;
import agentesdabolsa.entity.Agente;
import openjade.ontology.Rating;

public class BandwidthMetric extends AbstractMetric {

	private static boolean ON = false;

	public static long sum = 0;

	@Override
	public void beforePlay() {
		ON = true;
	}

	@Override
	public void afterPlay() {
		ON = false;
	}

	@Override
	public void add(Agente agente) {
	}

	@Override
	public double calc() {
		return sum / 1024;
	}

	@Override
	public IMetric init(int iteration) {
		if (iteration == 1) {
			sum = 0;
		}
		return this;
	}

	public static void count(Rating rating) {
		if (BandwidthMetric.ON) {
			sum += AppUtils.serialize(rating).length;
		}

	}
}
