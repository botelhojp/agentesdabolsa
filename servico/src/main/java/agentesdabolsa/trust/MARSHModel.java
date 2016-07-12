package agentesdabolsa.trust;

import agentesdabolsa.business.GameBC;
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
		if (++count < 10 || count % 10 == 0) {
			return ramdon();
		}
		return GameBC.getAgent(getBestByMe());
	}

	@Override
	public String getName() {
		return "MARSH (Direct)";
	}
	

}
