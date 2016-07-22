package agentesdabolsa.metric;

import java.util.GregorianCalendar;

import agentesdabolsa.entity.Agente;

public class CPUTimeAVGMetric extends AbstractMetric {
	
	private long start;
	private long end;
	private long sum;
	private long count;
	
	@Override
	public IMetric init(int iteration) {
		sum = count = 0;
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
		count++;		
	}
	

	@Override
	public double calc() {
		return sum / count;
	}



	


}
