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
public class DossieModel extends AbstractTrust {
	
	private static final long serialVersionUID = 1L;
	
	protected TrustData dossie = new TrustData(10);
	
	/**
	 * selecionar o melhor agente, porem precisa interagir com outros agente
	 * para montar seu modelo
	 */
	private long count = 0;

	@Override
	public Agente select() {
		if (++count < 10 || count % 10 == 0) {
			return ramdon();
		}
		return GameBC.getAgent(getBestByMe());
	}
	
	public AID getBestByMe() {
		AID rt = null;
		double aux = -999999.99;
		Iterator<AID> it = data.keySet().iterator();
		while (it.hasNext()) {
			AID aid = (AID) it.next();
			double sum = ((DossieModel) GameBC.getAgent(aid).getTrust()).getDossie();
			if (sum > aux) {
				rt = aid;
			}
		}
		return rt;
	}
	@Override
	public void addRating(Rating rating) {
		super.addRating(rating);
		Agente server = GameBC.getAgent(rating.getServer());
		((DossieModel)server.getTrust()).sendFeedback(rating);
	}
	
	/**
	 * Obtem uma avaliacao como feedback
	 */
	public void sendFeedback(Rating rating){
		dossie.addRating(rating);
	}
	
	public double getDossie(){
		return dossie.getSum();
	}
	
	
}
