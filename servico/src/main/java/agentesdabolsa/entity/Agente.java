package agentesdabolsa.entity;

public class Agente extends JSONBean {

	private String name;

	private Long clones;

	private String actionAfter;

	private String actionBefore;

	private String requestHelp;

	private String responseHelp;

	public Agente() {

	}

	public Agente(String name, long clones) {
		this.name = name;
		this.clones = clones;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getClones() {
		return clones;
	}

	public void setClones(Long clones) {
		this.clones = clones;
	}

	public String getActionAfter() {
		return actionAfter;
	}

	public void setActionAfter(String actionAfter) {
		this.actionAfter = actionAfter;
	}

	public String getActionBefore() {
		return actionBefore;
	}

	public void setActionBefore(String actionBefore) {
		this.actionBefore = actionBefore;
	}

	public String getRequestHelp() {
		return requestHelp;
	}

	public void setRequestHelp(String requestHelp) {
		this.requestHelp = requestHelp;
	}

	public String getResponseHelp() {
		return responseHelp;
	}

	public void setResponseHelp(String responseHelp) {
		this.responseHelp = responseHelp;
	}
}
