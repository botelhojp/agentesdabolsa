package agentesdabolsa.security;

import java.util.Hashtable;

public class TokenManager {
	
	private Hashtable<String, AppPrincipal> tokens;
	public static TokenManager instance;
	
	private TokenManager(){
		tokens = new Hashtable<String, AppPrincipal>();
	}

	public static TokenManager getInstance() {
		if (instance == null){
			instance = new TokenManager();
		}
		return instance;
	}
	
	public AppPrincipal getUser(String token){
		return tokens.get(token);
	}
	
	public void setUser(String token, AppPrincipal principal){
		tokens.put(token, principal);
	}

	public void clear() {
		tokens.clear();		
	}
}
