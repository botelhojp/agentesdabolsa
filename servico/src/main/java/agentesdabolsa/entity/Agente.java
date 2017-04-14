package agentesdabolsa.entity;

import agentesdabolsa.commons.AppUtils;
import agentesdabolsa.trust.ITrust;
import jade.core.AID;

public class Agente extends JSONBean {
	
	private AID aid;

	private String name;

	private Long clones;

	private String actionAfter;

	private String actionBefore;

	private String requestHelp;

	private String responseHelp;
	
	private String riskAHP;
	
	private Boolean enabled;

	private ITrust trust;
	
	public Agente(){
	}
	
	public Agente(AID aid, String name, long clones) {
		setAID(aid);
		setName(name);
		setClones(clones);
	}

	public Agente(String name, long clones) {
		setName(name);
		setClones(clones);
	}

	public Agente(long id) {
		super(id);
	}

	public Agente(Agente agente, int instance) {
		
	}

	public String getName() {
		if (name == null || name.isEmpty()){
			return "---";
		}
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

	public Boolean getEnabled() {
		if (enabled == null)
			return false;
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public AID getAID() {
		return aid;
	}

	public void setAID(AID aid) {
		this.aid = aid;
	}

	public void setTrust(ITrust trust) {
		this.trust = trust;
	}

	public ITrust getTrust() {
		return trust;
	}

	public String getRiskAHP() {
		return riskAHP;
	}

	public void setRiskAHP(String riskAHP) {
		this.riskAHP = riskAHP;
	}
}
