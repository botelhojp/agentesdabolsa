package agentesdabolsa.metric;

import java.util.GregorianCalendar;

import agentesdabolsa.entity.Agente;

public class TimeTotalMetric extends AbstractMetric {

	private long before;
	private long after;
	private long sum;

	@Override
	public IMetric init(int iteration) {
		if (iteration == 1){
			sum = 0;
		}
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
	}

	@Override
	public double calc() {
		return sum;
	}

}
