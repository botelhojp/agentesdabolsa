package agentesdabolsa.entity;

import com.google.gson.Gson;

public class JSONBean {
	
	@Override
	public String toString() {
		Gson g = new Gson();
		return g.toJson(this, this.getClass());
	}

}
