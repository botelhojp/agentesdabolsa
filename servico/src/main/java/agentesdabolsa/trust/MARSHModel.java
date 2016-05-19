package agentesdabolsa.trust;

import java.util.HashMap;

import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

public class MARSHModel  {
	
	protected HashMap<AID, TrustData> data;
	
	private Agente myAgent;

	public MARSHModel(Agente agente) {
		myAgent = agente;
	}

	public void setTest(Rating rating) {
		
	}
	public void addRating(Rating rt) {
		// TODO Auto-generated method stub
		
	}
}
