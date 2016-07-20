package agentesdabolsa.metric;

import agentesdabolsa.entity.Agente;

public interface IMetric {
	public void add(Agente agente);
	public double calc();
	public IMetric init(int iteration);
}
