package agentesdabolsa.trust;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

public interface ITrustModel extends Serializable {

	/** Agent */

	public void setAgent(Agente taskAgent);

	/** Rating */

	public void addRating(Rating rating);
	
	public void addRatingFromWitness(Rating rating);	

	public String test(AID aid);
	
	/** Propriedades */
	
	public Properties getProperties();

	public void setProperties(Properties properties);
	
	public void setTest(Rating rating);

	public void findReputation(AID server);

	public List<Rating> getDossie();

}
