package agentesdabolsa.entity.elk;

import agentesdabolsa.entity.Agente;

public class AgenteELK {

	private String _id;

	private Agente _source;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Agente get_source() {
		return _source;
	}

	public void set_source(Agente _source) {
		this._source = _source;
	}
}
