package agentesdabolsa;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.business.Log;
import agentesdabolsa.dao.AgenteDAO;
import agentesdabolsa.entity.Agente;

public class Simulador {
	
	public static void main(String[] args) {	
		Log.configure(true);
		AgenteDAO agenteDao = AgenteDAO.getInstance();
		
		GameBC.configure(100);
		
		GameBC game = GameBC.getInstance();
		for(Agente agente : agenteDao.list()){
			game.add(agente);
		}
		GameBC.start();
	}

}
