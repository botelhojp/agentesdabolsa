package agentesdabolsa.trust;

import agentesdabolsa.entity.Agente;

/**
 * Sem modelo de confiança, os agentes sao escolhidos aleatoriamente
 * @author Vanderson
 */
public class LessModel extends AbstractTrust {

	/**
	 * Seleciona aleatoriamente um agente
	 */
	@Override
	public Agente select() {
		return getRamdonAgent();
	}

	@Override
	public String getName() {
		return "Less (No Trust)";
	}


}
