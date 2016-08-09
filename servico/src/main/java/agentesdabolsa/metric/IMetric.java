package agentesdabolsa.metric;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.entity.Agente;

public interface IMetric {
	public void add(Agente agente);
	public double calc();
	public IMetric init(int iteration);
	public void beforePlay();
	public void afterPlay();
	public String getAgentPattern();
	public void setGameBC(GameBC gameBC);
}
