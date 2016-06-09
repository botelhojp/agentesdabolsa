package agentesdabolsa.trust;

import java.io.Serializable;

import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

public interface ITrust extends Serializable {

	public void addRating(Rating rating);
	
	public AID getBest();
	
	public void setAgent(Agente agente);
	
}
