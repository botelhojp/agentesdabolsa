package agentesdabolsa.trust;

import java.util.HashMap;

import agentesdabolsa.metric.BandwidthMetric;
import agentesdabolsa.metric.OperationMetric;
import jade.core.AID;
import openjade.ontology.Rating;

/**
 * Sem modelo de confiança, os agentes sao escolhidos aleatoriamente
 * 
 * @author Vanderson
 */
public class CentralModel extends AbstractTrust {

	protected static HashMap<AID, TrustData> centralData = new HashMap<AID, TrustData>();
	protected static HashMap<Integer, AID> cacheBest = new HashMap<Integer, AID>();

	/**
	 * Adicionar avaliacoes localmente
	 */
	public void addRating(Rating rating) {
		BandwidthMetric.count(rating);
		if (centralData.containsKey(rating.getServer())) {
			centralData.get(rating.getServer()).addRating(rating);
		} else {
			centralData.put(rating.getServer(), new TrustData(rating.getServer()));
			centralData.get(rating.getServer()).addRating(rating);
		}
	}

	/**
	 * Melhor agente na visao local dele
	 */
	public AID getBestByMe() {
		OperationMetric.count();
		if (cacheBest.containsKey(getIteration())) {
			return cacheBest.get(getIteration());
		}
		AID rt = super.getBestByMe(centralData);
		if (rt != null) {
			cacheBest.put(getIteration(), rt);
		}
		return rt;
	}

	@Override
	public String getName() {
		return "Central";
	}

	@Override
	public void setIteration(Integer iteration) {
		super.setIteration(iteration);
		if (iteration == 1) {
			centralData.clear();
			cacheBest.clear();
		}
	}
}
