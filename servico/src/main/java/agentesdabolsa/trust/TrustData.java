package agentesdabolsa.trust;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import agentesdabolsa.metric.OperationMetric;
import jade.core.AID;
import jade.util.leap.Serializable;
import openjade.ontology.Rating;

public class TrustData implements Comparator<TrustData>, Serializable {

	private static final long serialVersionUID = 1L;
	protected List<Rating> ratings = new ArrayList<Rating>();
	private double sum = 0.0;
	private long max_size = Long.MAX_VALUE;
	private AID aid;

	public TrustData(AID aid) {
		this(aid, 200);
	}

	public TrustData(AID aid, int size) {
		this.aid = aid;
		this.max_size = size;
		OperationMetric.count();
	}

	public void addRating(Rating rating) {
		OperationMetric.count();
		if (ratings.size() == max_size) {
			Rating r = ratings.remove(0);
			sum -= Double.parseDouble(r.getValue());
		}
		ratings.add(rating);
		sum += Double.parseDouble(rating.getValue());
	}

	public List<Rating> getRatings() {
		OperationMetric.count();
		return ratings;
	}

	public double getSum() {
		return sum;
	}

	public AID getAID() {
		return aid;
	}

	public int compare(TrustData o1, TrustData o2) {
		OperationMetric.count();
		if (o1.getSum() > o1.getSum()) {
			return 1;
		}
		if (o1.getSum() < o1.getSum()) {
			return -1;
		}
		return 0;
	}

	public int size() {
		return ratings.size();
	}
}
