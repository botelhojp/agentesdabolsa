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
//		List<Agente> agents = GameBC.getAgents();
//		for (int i = 0; i < 10; i++) {
//			int index = (int) Math.round((agents.size() - 1) * Random.getNumer());
//			Agente select = agents.get(index);
//			if (!select.getAID().equals(myAgent.getAID()) && select.getResponseHelp() != null && !select.getResponseHelp().isEmpty()) {
//				return select;
//			}
//		}
//		return null;
		return ramdon();
	}

	@Override
	public String getName() {
		return "Less (No Trust)";
	}


}
