package agentesdabolsa.entity;

public class Config extends JSONBean {

	private String acoes;

	private Integer stop;

	private Integer startTrust;

	private Integer randomSeed;
	
	private Integer changes;
	
	private String changeType;
	
	private Integer iterationTotal;

	private Integer prequentialFadingFactor;

	private Boolean random;

	private Boolean malice;

	private String agentPattern;

	public Integer getPrequentialFadingFactor() {
		return prequentialFadingFactor;
	}

	public Integer getIterationTotal() {
		return iterationTotal;
	}

	public Integer getChanges() {
		return changes;
	}

	public void setChanges(Integer changes) {
		this.changes = changes;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public void setIterationTotal(Integer iterationTotal) {
		this.iterationTotal = iterationTotal;
	}

	public void setPrequentialFadingFactor(Integer prequentialFadingFactor) {
		this.prequentialFadingFactor = prequentialFadingFactor;
	}

	public String getAgentPattern() {
		return agentPattern;
	}

	public void setAgentPattern(String agentPattern) {
		this.agentPattern = agentPattern;
	}

	public String getAcoes() {
		return acoes;
	}

	public void setAcoes(String acoes) {
		this.acoes = acoes;
	}

	public Boolean getRandom() {
		return random;
	}

	public void setRandom(Boolean random) {
		this.random = random;
	}

	public Integer getStop() {
		return stop;
	}

	public void setStop(Integer stop) {
		this.stop = stop;
	}

	public Integer getRandomSeed() {
		return randomSeed;
	}

	public void setRandomSeed(Integer randomSeed) {
		this.randomSeed = randomSeed;
	}

	public Integer getStartTrust() {
		return startTrust;
	}

	public void setStartTrust(Integer startTrust) {
		this.startTrust = startTrust;
	}

	public Boolean getMalice() {
		return malice;
	}

	public void setMalice(Boolean malice) {
		this.malice = malice;
	}

}
