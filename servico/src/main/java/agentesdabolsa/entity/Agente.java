package agentesdabolsa.entity;

import agentesdabolsa.commons.AppUtils;

public class Agente extends JSONBean {

	private String name;

	private Long clones;

	private String actionAfter;

	private String actionBefore;

	private String requestHelp;

	private String responseHelp;
	
	public Agente(){
	}

	public Agente(String name, long clones) {
		setName(name);
		setClones(clones);
	}

	public Agente(long id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = AppUtils.normalize(name);
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
		this.actionAfter = AppUtils.normalize(actionAfter);
	}

	public String getActionBefore() {
		return actionBefore;
	}

	public void setActionBefore(String actionBefore) {
		this.actionBefore = AppUtils.normalize(actionBefore);
	}

	public String getRequestHelp() {
		return requestHelp;
	}

	public void setRequestHelp(String requestHelp) {
		this.requestHelp = AppUtils.normalize(requestHelp);
	}

	public String getResponseHelp() {
		return responseHelp;
	}

	public void setResponseHelp(String responseHelp) {
		this.responseHelp = AppUtils.normalize(responseHelp);
	}
}
