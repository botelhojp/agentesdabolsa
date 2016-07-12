package agentesdabolsa.trust;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.entity.Agente;

/**
 * Modelo Direto
 * @author Vanderson
 */
public class MARSHModel extends AbstractTrust {

	private static final long serialVersionUID = 1L;

	@Override
	public Agente select() {
		if (++count < startTrust || count % startTrust == 0) {
			return ramdon();
		}
		return GameBC.getAgent(getBestByMe());
	}

	@Override
	public String getName() {
		return "MARSH (Direct)";
	}
	

}
