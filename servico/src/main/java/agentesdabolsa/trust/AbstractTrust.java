package agentesdabolsa.trust;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.business.Random;
import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

public abstract class AbstractTrust implements ITrust {

	private static final long serialVersionUID = 1L;

	protected Agente myAgent;
	protected HashMap<AID, TrustData> data;
	protected Integer iteration;

	public AbstractTrust() {
		data = new HashMap<AID, TrustData>();
	}
	
	public void setIteration(Integer iteration){
		this.iteration =  iteration;
	}

	public Integer getIteration() {
		return iteration;
	}

	/**
	 * Melhor agente na visao local dele
	 */
	public AID getBestByMe() {
		AID rt = null;
		double aux = -99999999.99;
		Iterator<AID> it = data.keySet().iterator();
		while (it.hasNext()) {
			AID aid = (AID) it.next();
			TrustData d = data.get(aid);
			if (d.getSum() > aux) {
				rt = aid;
				aux = d.getSum();
			}
		}
		return rt;
	}

	/**
	 * Adicionar avaliacoes localmente
	 */
	public void addRating(Rating rating) {
		if (data.containsKey(rating.getServer())) {
			data.get(rating.getServer()).addRating(rating);
		} else {
			data.put(rating.getServer(), new TrustData(rating.getServer()));
			data.get(rating.getServer()).addRating(rating);
		}
	}

	public void setAgent(Agente agente) {
		this.myAgent = agente;
	}
	
	protected Agente ramdon() {
		List<Agente> agents = GameBC.getAgents();
		for (int i = 0; i < 50; i++) {
			int index = (int) Math.round((agents.size() - 1) * Random.getNumer());
			Agente select = agents.get(index);
			if (!select.getAID().equals(myAgent.getAID()) && select.getResponseHelp() != null && !select.getResponseHelp().isEmpty()) {
				return select;
			}
		}
		return null;
	}
}
