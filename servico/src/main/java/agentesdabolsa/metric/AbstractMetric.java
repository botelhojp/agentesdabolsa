package agentesdabolsa.metric;

import agentesdabolsa.business.ConfigBC;
import agentesdabolsa.business.GameBC;
import agentesdabolsa.entity.Agente;

public abstract class AbstractMetric implements IMetric {
	
	private String agentPattern;
	protected Integer prequentialFadingFactor;
	protected GameBC gameBC;
	
	public AbstractMetric(){
		agentPattern = ConfigBC.getInstance().getConfig().getAgentPattern();
		prequentialFadingFactor = ConfigBC.getInstance().getConfig().getPrequentialFadingFactor();
	}

	public abstract void add(Agente agente);

	public abstract double calc();

	public abstract IMetric init(int iteration);
	
	public void setGameBC(GameBC gameBC){
		this.gameBC = gameBC;
	}
	
	public void afterPlay() {
	}
	
	public void beforePlay() {
	}
	
	@Override
	public String getAgentPattern() {
		return agentPattern;
	}

}
