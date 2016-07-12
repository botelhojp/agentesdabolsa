package agentesdabolsa.trust;

import java.util.HashMap;
import java.util.Iterator;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

/**
 * Sem modelo de confiança, os agentes sao escolhidos aleatoriamente
 * 
 * @author Vanderson
 */
public class CentralModel extends AbstractTrust {

	private static final long serialVersionUID = 1L;

	protected static HashMap<AID, TrustData> centralData = new HashMap<AID, TrustData>();
	protected static HashMap<Integer, AID> cacheBest = new HashMap<Integer, AID>();
	private long count = 0;

	/**
	 * Seleciona aleatoriamente um agente
	 */
	@Override
	public Agente select() {
		if (++count < 10 || count % 10 == 0) {
			return ramdon();
		}
		return GameBC.getAgent(getBestByMe());
	}

	/**
	 * Adicionar avaliacoes localmente
	 */
	public void addRating(Rating rating) {
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
		if (cacheBest.containsKey(getIteration())) {
			return cacheBest.get(getIteration());
		}
		AID rt = null;
		double aux = -99999999.99;
		Iterator<AID> it = centralData.keySet().iterator();
		while (it.hasNext()) {
			AID aid = (AID) it.next();
			TrustData d = centralData.get(aid);
			if (d.getSum() > aux) {
				rt = aid;
				aux = d.getSum();
			}
		}
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
