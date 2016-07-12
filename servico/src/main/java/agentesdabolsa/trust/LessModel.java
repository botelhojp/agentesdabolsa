package agentesdabolsa.trust;

import agentesdabolsa.entity.Agente;

/**
 * Sem modelo de confiança, os agentes sao escolhidos aleatoriamente
 * @author Vanderson
 */
public class LessModel extends AbstractTrust {

	private static final long serialVersionUID = 1L;

	/**
	 * Seleciona aleatoriamente um agente
	 */
	@Override
	public Agente select() {
		return ramdon();
	}

	@Override
	public String getName() {
		return "Less (No Trust)";
	}


}
