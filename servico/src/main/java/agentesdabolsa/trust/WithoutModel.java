package agentesdabolsa.trust;

import java.util.List;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.business.Random;
import agentesdabolsa.entity.Agente;

/**
 * Sem modelo de confian�a, os agentes sao escolhidos aleatoriamente
 * @author Vanderson
 */
public class WithoutModel extends AbstractTrust {

	private static final long serialVersionUID = 1L;

	/**
	 * Seleciona aleatoriamente um agente
	 */
	@Override
	public Agente select() {
		List<Agente> agents = GameBC.getAgents();
		for (int i = 0; i < 10; i++) {
			int index = (int) Math.round((agents.size() - 1) * Random.getNumer());
			Agente select = agents.get(index);
			if (!select.getAID().equals(myAgent.getAID()) && select.getResponseHelp() != null && !select.getResponseHelp().isEmpty()) {
				return select;
			}
		}
		return null;
	}


}