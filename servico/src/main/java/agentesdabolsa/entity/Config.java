package agentesdabolsa.entity;

public class Config extends JSONBean {

	private String acoes;
	
	private Integer stop;

	private Boolean random;

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
	
	
}
