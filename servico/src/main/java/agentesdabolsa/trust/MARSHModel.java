package agentesdabolsa.trust;

import java.util.HashMap;
import java.util.Iterator;

import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

public class MARSHModel implements ITrust {
	
	private static final long serialVersionUID = 1L;

	protected HashMap<AID, TrustData> data;
	
	private Agente myAgent;
	
	public MARSHModel() {
		data = new HashMap<AID, TrustData>();
	}

	public MARSHModel(Agente agente) {
		this();
		myAgent = agente;
	}
	
	public AID getBest() {
		AID rt = null;
		double aux = Double.MIN_VALUE;
		Iterator<AID> it = data.keySet().iterator();
		while (it.hasNext()) {
			AID aid = (AID) it.next();
			TrustData d = data.get(aid);
			if (d.getSum() > aux){
				rt = aid;
			}
		}
		return rt;
	}

	public void addRating(Rating rating) {
		if (isIamClient(rating)) {
			addRatingFromWitness(rating);
		}
	}
	
	protected boolean isIamClient(Rating rating) {
		return (rating != null && rating.getClient().equals(myAgent.getAID()));
	}
	
	public void addRatingFromWitness(Rating rating) {
		if (data.containsKey(rating.getServer())) {
			data.get(rating.getServer()).addRating(rating);
		} else {
			data.put(rating.getServer(), new TrustData());
			data.get(rating.getServer()).addRating(rating);
		}		
	}

	public void setAgent(Agente agente) {
		myAgent = agente;
	}
}
