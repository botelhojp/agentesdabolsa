package agentesdabolsa.metric;

import agentesdabolsa.entity.Agente;

public class MessageMetric extends AbstractMetric {

	private static boolean ON = false;

	public static long count = 0;

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
		return count;
	}

	@Override
	public IMetric init(int iteration) {
		if (iteration == 1) {
			count = 0;
		}
		return this;
	}

	public static void count() {
		if (MessageMetric.ON) {
			count += 1;
		}
	}
}
