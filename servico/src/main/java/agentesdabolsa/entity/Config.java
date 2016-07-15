package agentesdabolsa.entity;

public class Config extends JSONBean {

	private String acoes;
	
	private Integer stop;
	
	private Integer startTrust;
	
	private Integer randomSeed;

	private Boolean random;
	
	private Boolean malice;

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
