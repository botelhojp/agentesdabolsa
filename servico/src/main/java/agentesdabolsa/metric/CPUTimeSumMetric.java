package agentesdabolsa.metric;

import java.util.GregorianCalendar;

import agentesdabolsa.entity.Agente;

public class CPUTimeSumMetric extends AbstractMetric {

	private long start;
	private long end;
	private long sum;

	@Override
	public IMetric init(int iteration) {
		if (iteration == 1) {
			sum = 0;
		}
		return this;
	}

	public void beforePlay() {
		start = GregorianCalendar.getInstance().getTimeInMillis();
	}

	public void afterPlay() {
		end = GregorianCalendar.getInstance().getTimeInMillis();
		sum += (end - start);
	}

	@Override
	public void add(Agente agente) {
	}

	@Override
	public double calc() {
		return sum;
	}

}
