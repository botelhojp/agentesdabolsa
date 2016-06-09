package agentesdabolsa.trust;

import java.util.ArrayList;
import java.util.List;

import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

public class WithoutModel implements ITrust {

	private static final long serialVersionUID = 1L;

	List<AID> list = new ArrayList<AID>();
	
	public void addRating(Rating rating) {
		list.add(rating.getServer());
		
	}

	public AID getBest() {
		long from = Math.round((list.size()-1) * Math.random());
		return list.get((int)from);
	}

	public void setAgent(Agente agente) {
	}
	
	
}
