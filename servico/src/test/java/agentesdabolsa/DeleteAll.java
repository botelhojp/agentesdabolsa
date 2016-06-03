package agentesdabolsa;

import java.util.List;

import agentesdabolsa.business.GameBC;
import agentesdabolsa.business.Log;
import agentesdabolsa.dao.AcaoDAO;
import agentesdabolsa.dao.AgenteDAO;
import agentesdabolsa.dao.CotacaoDAO;
import agentesdabolsa.entity.Agente;
import agentesdabolsa.entity.Cotacao;

public class DeleteAll {
	
	public static void main(String[] args) {
		AcaoDAO.getInstance().deleteAll();
		//CotacaoDAO.getInstance().deleteAll();
		
	}

}
