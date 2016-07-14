package agentesdabolsa.trust;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.entity.Agente;

/**
 * Modelo baseado em testemunhos
 * 
 * @author Vanderson
 */
public class FIREModel extends AbstractTrust {
	
	private static final long serialVersionUID = 1L;
	
	protected TrustData dossie;
	
	public FIREModel() {
	}

	@Override
	public Agente select() {
		if (++count < startTrust || count % startTrust == 0) {
			return ramdon();
		}
		return GameBC.getAgent(getBestByMe());
	}
	

	@Override
	public String getName() {
		return "FIRE (Witness)";
	}
}
