package agentesdabolsa.security;

import java.security.Principal;

public class AppPrincipal implements Principal {
	
	private String name;
	private String role;

	@Override
	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setName(String name) {
		this.name = name;
	}
}
