package agentesdabolsa.exception;

import java.util.Hashtable;

import javax.ws.rs.core.Response.Status;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private Hashtable<String, String> entity = new Hashtable<String, String>();
	private Status status;
	
	public AppException(String msg, Exception e) {
		super(msg, e);
	}

	public AppException(Status status, String... msg) {
		entity = new Hashtable<String, String>();
		for (int i = 0; i < msg.length; i = i + 2) {
			entity.put(msg[i], msg[i+1]);
		}
		this.status = status;
	}

	public AppException(String string) {
		super(string);
	}

	public Status getStatus() {
		return status;
	}

	public Hashtable<String, String> getEntity() {
		return entity;
	}
	
	
}
