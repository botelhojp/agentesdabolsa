package agentesdabolsa.trust;

import java.util.Iterator;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.entity.Agente;
import jade.core.AID;
import openjade.ontology.Rating;

/**
 * Modelo de Dossie
 * 
 * @author VandersonLocal
 */
public class ICEModel extends AbstractTrust {
	
	private static final int DOSSIE_SIZE = 10;
	protected TrustData dossie;
	
	public ICEModel() {
	}
	
	@Override
	public Agente select() {
		if (++count < startTrust || count % startTrust == 0) {
			return getRamdonAgent();
		}
		return GameBC.getAgent(getBestByMe());
	}
	
	public AID getBestByMe() {
		AID rt = null;
		double aux = -999999.99;
		Iterator<AID> it = localData.keySet().iterator();
		while (it.hasNext()) {
			AID aid = (AID) it.next();
			double sum = ((ICEModel) GameBC.getAgent(aid).getTrust()).getDossie();
			if (sum > aux) {
				rt = aid;
				aux = sum;
			}
		}
		return rt;
	}
	@Override
	public void addRating(Rating rating) {
		super.addRating(rating);
		Agente server = GameBC.getAgent(rating.getServer());
		((ICEModel)server.getTrust()).sendFeedback(rating);
	}
	
	/**
	 * Obtem uma avaliacao como feedback
	 */
	public void sendFeedback(Rating rating){
		dossie.addRating(rating);
	}
	
	public double getDossie(){
		return dossie.getSum()/dossie.size();
	}
	
	@Override
	public void setAgent(Agente agente) {
		super.setAgent(agente);
		this.dossie = new TrustData(agente.getAID(), DOSSIE_SIZE);
	}

	@Override
	public String getName() {
		return "ICE (Dossie)";
	}
}
