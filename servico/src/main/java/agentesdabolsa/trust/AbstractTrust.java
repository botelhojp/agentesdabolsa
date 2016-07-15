package agentesdabolsa.trust;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import agentesdabolsa.business.ConfigBC;
import agentesdabolsa.business.GameBC;
import agentesdabolsa.business.Random;
import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

public abstract class AbstractTrust implements ITrust {

	protected Agente myAgent;
	protected HashMap<AID, TrustData> localData;
	protected Integer iteration;
	protected Integer startTrust;
	protected long count = 0;

	public AbstractTrust() {
		startTrust = ConfigBC.getInstance().getConfig().getStartTrust();
		localData = new HashMap<AID, TrustData>();
	}
	
	public void setIteration(Integer _iteration){
		this.iteration =  _iteration;
	}

	public Integer getIteration() {
		return iteration;
	}

	/**
	 * Melhor agente na visao local dele
	 */
	public AID getBestByMe() {
		return getBestByMe(localData);
	}
	
	protected AID getBestByMe(HashMap<AID, TrustData> _trustData) {
		AID _return = null;
		double auxValue = -99999999.99;
		Iterator<AID> agents = _trustData.keySet().iterator();
		while (agents.hasNext()) {
			AID agentAID = (AID) agents.next();
			TrustData data = _trustData.get(agentAID);
			if (data.getSum() > auxValue) {
				_return = agentAID;
				auxValue = data.getSum();
			}
		}
		return _return;
	}
	
	@Override
	public Agente select() {
		if (++count < startTrust || count % startTrust == 0) {
			return getRamdonAgent();
		}
		return GameBC.getAgent(getBestByMe());
	}

	/**
	 * Adicionar avaliacoes localmente
	 */
	public void addRating(Rating rating) {
		if (localData.containsKey(rating.getServer())) {
			localData.get(rating.getServer()).addRating(rating);
		} else {
			localData.put(rating.getServer(), new TrustData(rating.getServer()));
			localData.get(rating.getServer()).addRating(rating);
		}
	}

	public void setAgent(Agente agente) {
		this.myAgent = agente;
	}
	
	protected Agente getRamdonAgent() {
		List<Agente> agents = GameBC.getAgents();
		for (int i = 0; i < 100; i++) {
			int index = (int) Math.round((agents.size() - 1) * Random.getNumer());
			Agente select = agents.get(index);
			if (!select.getAID().equals(myAgent.getAID()) && select.getResponseHelp() != null && !select.getResponseHelp().isEmpty()) {
				return select;
			}
		}
		return null;
	}
}
