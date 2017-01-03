package agentesdabolsa.metric;

import agentesdabolsa.entity.Agente;

public class OperationMetric extends AbstractMetric {

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
		return sum;
	}

	@Override
	public IMetric init(int iteration) {
		if (iteration == 1) {
			sum = 0;
		}
		return this;
	}

	public static void count() {
		if (OperationMetric.ON) {
			sum += 1;
		}

	}
}
