package agentesdabolsa.metric;

import agentesdabolsa.entity.Agente;

public class MemoryMetric implements IMetric {


	@Override
	public IMetric init() {
		return this;
	}

	@Override
	public void add(Agente agente) {
	}

	@Override
	public double calc() {
		Runtime r = Runtime.getRuntime();
		return (r.totalMemory() -  Runtime.getRuntime().freeMemory()) / (1024*1024);
	}



}
