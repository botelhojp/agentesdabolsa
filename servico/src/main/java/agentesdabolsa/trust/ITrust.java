package agentesdabolsa.trust;

import java.io.Serializable;

import agentesdabolsa.entity.Agente;
import openjade.ontology.Rating;

public interface ITrust extends Serializable {

	public void addRating(Rating rating);
	
	public void setAgent(Agente agente);

	public Agente select();
	
}
