package agentesdabolsa.entity;

import agentesdabolsa.commons.StringUtils;

public class FileSerie extends JSONBean {

	private String name;

	public FileSerie() {
	}

	public FileSerie(String name) {
		setName(name);
	}

	public FileSerie(long id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtils.normalize(name);
	}

}
