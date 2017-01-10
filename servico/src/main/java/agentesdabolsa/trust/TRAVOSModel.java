package agentesdabolsa.trust;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.metric.MessageMetric;
import agentesdabolsa.metric.OperationMetric;
import jade.core.AID;
import openjade.ontology.Rating;

/**
 * Modelo baseado em testemunhos.
 * Nessa implementacao as testemunha sao os proprios agentes
 * que interagem entre si.
 * 
 * @author Vanderson
 */
public class TRAVOSModel extends AbstractTrust {

	private List<AID> witnesses;

	public TRAVOSModel() {
		super();
		witnesses = new ArrayList<AID>();
	}

	public void addRating(Rating rating) {
		super.addRating(rating);
		OperationMetric.count();
		witnesses.add(rating.getServer());
	}
	
	/**
	 * Melhor agente na visao local dele
	 */
	public AID getBestByMe() {
		AID _return = null;
		double auxValue = -99999999.99;
		Iterator<AID> agents = localData.keySet().iterator();
		while (agents.hasNext()) {
			OperationMetric.count();
			AID serverAID = (AID) agents.next();
			TrustData data = localData.get(serverAID);
			if ((data.getSum() + getValueByWitness(serverAID))  > auxValue) {
				_return = serverAID;
				auxValue = data.getSum();
			}
		}
		return _return;
	}

	private double getValueByWitness(AID serverAID) {
		double rt = 0;
		for (AID witnessAID : witnesses) {
			OperationMetric.count();
			Agente witnessAgent = GameBC.getAgent(witnessAID);
			TRAVOSModel witnessFire = ((TRAVOSModel) witnessAgent.getTrust());
			if (witnessFire.know(serverAID)){
				Double s = witnessFire.getValue(serverAID);
				MessageMetric.count();
				rt += s;
			}
		}
		return rt;
	}

	private double getValue(AID serverAID) {
		return localData.get(serverAID).getSum();
	}

	private boolean know(AID serverAID) {
		OperationMetric.count();
		MessageMetric.count();
		return localData.containsKey(serverAID);
	}

	@Override
	public String getName() {
		return "TRAVOS (Witness)";
	}
}
