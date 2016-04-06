package agentesdabolsa.exception;

public class AppExceptions extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AppExceptions(String msg, Exception e) {
		super(msg, e);
	}

	public AppExceptions(String msg) {
		super(msg);
	}
	
	
}
