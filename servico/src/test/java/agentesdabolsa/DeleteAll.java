package agentesdabolsa;

import agentesdabolsa.dao.AcaoDAO;

public class DeleteAll {
	
	public static void main(String[] args) {
		AcaoDAO.getInstance().deleteAll();
		//CotacaoDAO.getInstance().deleteAll();
		
	}

}
