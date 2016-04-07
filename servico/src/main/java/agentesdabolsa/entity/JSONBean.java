package agentesdabolsa.entity;

import com.google.gson.Gson;

public class JSONBean {

	protected long id;

	public JSONBean() {
	}

	public JSONBean(long id) {
		super();
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		Gson g = new Gson();
		return g.toJson(this, this.getClass());
	}

}
