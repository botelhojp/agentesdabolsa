package agentesdabolsa.trust;

import openjade.ontology.Rating;

/**
 * Modelo Direto
 * @author Vanderson
 */
public class MARSHModel extends AbstractTrust {

	@Override
	public String getName() {
		return "MARSH (Direct)";
	}
	
	@Override
	public void addRating(Rating rating) {
		super.addRating(rating);
	}
	

}
