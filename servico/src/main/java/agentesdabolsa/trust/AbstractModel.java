package agentesdabolsa.trust;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;
import openjade.trust.model.Pair;

public  class AbstractModel implements ITrustModel {

	protected HashMap<AID, TrustData> data;
	protected int currentIteration;
	protected Agente myAgent;
	protected Properties properties;
	protected Rating test;

	private static final long serialVersionUID = 1L;

	public AbstractModel() {
		data = new HashMap<AID, TrustData>();
		properties = new Properties();
	}

	public String test(AID aid) {
//		double sum = 0.0;
//		double count = 0.0;
//		Clazz avaliado = null;
//		for(Rating r : getRatings(aid)){
//			double delta = (r.getRound())/ (double) (test.getRound());
//			count+=delta;
//			Clazz esperado = Classes.getClass(r.getValue());			
//			sum += (esperado.getValue()*delta);			
//		}
//		addRating(test);
//		avaliado = (count == 0)? Classes.getClasses().get(0) : Classes.getClass((sum / count));
//		if (avaliado.getValue() > 0){
//			return "AGREE;" + test.getRound();
//		}else{
//			return "REFUSE;" + test.getRound();
//		}
		return null;
	}
	
	public void addRating(Rating rating) {
		if (isIamClient(rating)) {
			addRatingFromWitness(rating);
		}
	}
	
	public void addRatingFromWitness(Rating rating) {
		if (data.containsKey(rating.getServer())) {
			data.get(rating.getServer()).addRating(rating);
		} else {
			data.put(rating.getServer(), new TrustData());
			data.get(rating.getServer()).addRating(rating);
		}		
	}

	protected boolean isIamClient(Rating rating) {
		return (rating != null && rating.getClient().equals(myAgent.getAID()));
	}

	protected boolean isIamServer(Rating rating) {
		return (rating != null && rating.getServer().equals(myAgent.getAID()));
	}

	public Properties getProperties() {
		return properties;
	}

	public void currentIteration(int iteration) {
		currentIteration = iteration;
	}

	public List<Pair> getPairs(String[] terms) {
		return null;
	}

	public float getValue(AID server) {
		return 0;
	}

	public void setAgent(Agente agent) {
		this.myAgent = (Agente) agent;
	}

	public Iterator<AID> getAllServer() {
		return data.keySet().iterator();
	}

	public boolean know(AID aid) {
		return data.containsKey(aid);
	}

	/** Properties */

	protected double getDouble(String key) {
		return Double.parseDouble(properties.getProperty(key));
	}

	protected long getLong(String key) {
		return Long.parseLong(properties.getProperty(key));
	}

	protected long getInt(String key) {
		return Integer.parseInt(properties.getProperty(key));
	}

	protected String getValue(String key) {
		return properties.getProperty(key).toString();
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void findReputation(AID server) {
	}

	public List<Rating> getDossie() {
		return null;
	}

	public void clean() {
		data.clear();
	}

	public void setTest(Rating rating) {
		this.test = rating;		
		if (!data.containsKey(rating.getServer())) {
			data.put(rating.getServer(), new TrustData());
		}
	}
	
	public List<Rating> getRatings(AID aid) {		
		return data.get(aid).getRatings();
	}

}
