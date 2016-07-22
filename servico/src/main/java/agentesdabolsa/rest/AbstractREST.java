package agentesdabolsa.rest;

import agentesdabolsa.dao.ELKDAO;

public class AbstractREST {
	
	public AbstractREST(){
		ELKDAO.enabledCache(false);
	}

}
