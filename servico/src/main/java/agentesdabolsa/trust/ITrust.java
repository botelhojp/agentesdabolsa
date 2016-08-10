package agentesdabolsa.trust;

import agentesdabolsa.entity.Agente;
import openjade.ontology.Rating;

public interface ITrust {

	public void addRating(Rating rating);
	
	public void setAgent(Agente agente);
	
	public void setIteration(Integer iteration);

	public Agente select();
	
	public String getName();
}
