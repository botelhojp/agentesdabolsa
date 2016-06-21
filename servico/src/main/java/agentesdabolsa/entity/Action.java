package agentesdabolsa.entity;

public enum Action {
	BUY, SELL, WAIT, REQUEST_ANYONE;
	
	public Action reverse(){
		if (this.equals(BUY)){ return SELL; }
		if (this.equals(SELL)){ return BUY; }
		return null;
	}
}
