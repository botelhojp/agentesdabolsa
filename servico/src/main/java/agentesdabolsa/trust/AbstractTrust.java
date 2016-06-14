package agentesdabolsa.trust;

import java.util.List;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.entity.Agente;

public abstract class AbstractTrust implements ITrust {

	private static final long serialVersionUID = 1L;

	protected Agente myAgent;

	/**
	 * seleciona um agente aleatoriamente
	 */
	public Agente select() {
		List<Agente> agents = GameBC.getAgents();
		for (int i = 0; i < 10; i++) {
			int index = (int) Math.round((agents.size() - 1) * Math.random());
			Agente select = agents.get(index);
			if (!select.getAID().equals(myAgent.getAID()) && select.getResponseHelp() != null && !select.getResponseHelp().isEmpty()) {
				return select;
			}
		}
		return null;
	}

	public void setAgent(Agente agente) {
		this.myAgent = agente;
	}
}
