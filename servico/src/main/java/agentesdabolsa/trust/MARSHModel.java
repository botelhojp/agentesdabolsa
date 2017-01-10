package agentesdabolsa.trust;

import agentesdabolsa.metric.MessageMetric;
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
		MessageMetric.count();
		super.addRating(rating);
	}
	

}
