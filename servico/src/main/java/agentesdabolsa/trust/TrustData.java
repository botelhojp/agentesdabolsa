package agentesdabolsa.trust;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import jade.core.AID;
import openjade.ontology.Rating;

public class TrustData implements Serializable {

	private static final long serialVersionUID = 1L;
	protected List<Rating> ratings = new ArrayList<Rating>();
	protected Hashtable<Integer, Rating> hash = new Hashtable<Integer, Rating>();
	protected HashMap<AID, TrustData> data;
	private double sum = 0.0;

	public TrustData() {
	}
	
	public void addRating(Rating rating) {
		if (true){		
			ratings.add(rating);	
			hash.put(rating.getRound(), rating);
			sum += Double.parseDouble(rating.getValue());
		}		
	}

	public List<Rating> getRatings() {
		return ratings;
	}
	
	public double getSum(){
		return sum;
	}
}
