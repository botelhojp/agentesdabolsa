package agentesdabolsa.trust;

import java.util.List;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.business.Random;
import agentesdabolsa.entity.Agente;

/**
 * Modelo Direto
 * @author Vanderson
 */
public class MARSHModel extends AbstractTrust {

	private static final long serialVersionUID = 1L;

	/**
	 * selecionar o melhor agente, porem precisa interagir com outros agente
	 * para montar seu modelo
	 */
	private long count = 0;

	@Override
	public Agente select() {
		count++;
		if (count < 10 || count % 10 == 0) {
			return ramdon();
		}
		return GameBC.getAgent(getBestByMe());
	}
	
	private Agente ramdon() {
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
