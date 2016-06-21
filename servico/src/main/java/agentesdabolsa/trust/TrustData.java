package agentesdabolsa.trust;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jade.core.AID;
import openjade.ontology.Rating;

public class TrustData implements Serializable {

	private static final long serialVersionUID = 1L;
	protected List<Rating> ratings = new ArrayList<Rating>();
	protected HashMap<AID, TrustData> data;
	private double sum = 0.0;
	private long size = Long.MAX_VALUE;

	public TrustData() {
		this(200);
	}

	public TrustData(int size) {
		this.size = size;
	}

	public void addRating(Rating rating) {
		if (ratings.size() == size) {
			Rating r = ratings.remove(0);
			sum -= Double.parseDouble(r.getValue());
		}
		ratings.add(rating);
		sum += Double.parseDouble(rating.getValue());
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public double getSum() {
		return sum;
	}
}
