package agentesdabolsa.exception;

public class AppException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AppException(String msg, Exception e) {
		super(msg, e);
	}

	public AppException(String msg) {
		super(msg);
	}
	
	
}
