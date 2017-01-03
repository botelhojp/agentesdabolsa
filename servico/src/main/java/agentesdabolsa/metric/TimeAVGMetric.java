package agentesdabolsa.metric;

import java.util.GregorianCalendar;

import agentesdabolsa.entity.Agente;

public class TimeAVGMetric extends AbstractMetric {

	private long before;
	private long after;
	private long sum;
	private int count;

	@Override
	public IMetric init(int iteration) {
		sum = 0;
		count = 0;
		return this;
	}

	@Override
	public void beforePlay() {
		before = GregorianCalendar.getInstance().getTimeInMillis();
	}

	@Override
	public void afterPlay() {
		after = GregorianCalendar.getInstance().getTimeInMillis();
		sum += after - before;
	}

	@Override
	public void add(Agente agente) {
		count++;
	}

	@Override
	public double calc() {
		return sum / count;
	}

}
