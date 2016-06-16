package agentesdabolsa.trust;

import java.util.HashMap;
import java.util.Iterator;

import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

public abstract class AbstractTrust implements ITrust {

	private static final long serialVersionUID = 1L;

	protected Agente myAgent;
	protected HashMap<AID, TrustData> data;

	public AbstractTrust() {
		data = new HashMap<AID, TrustData>();
	}



	/**
	 * Melhor agente na visao local dele
	 */
	public AID getBestByMe() {
		AID rt = null;
		double aux = Double.MIN_VALUE;
		Iterator<AID> it = data.keySet().iterator();
		while (it.hasNext()) {
			AID aid = (AID) it.next();
			TrustData d = data.get(aid);
			if (d.getSum() > aux) {
				rt = aid;
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
			data.put(rating.getServer(), new TrustData());
			data.get(rating.getServer()).addRating(rating);
		}
	}

	public void setAgent(Agente agente) {
		this.myAgent = agente;
	}
}
