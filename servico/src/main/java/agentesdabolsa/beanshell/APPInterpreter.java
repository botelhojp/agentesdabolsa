package agentesdabolsa.beanshell;

import bsh.Interpreter;

public class APPInterpreter {
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static void main(String[] args) throws Exception {
		Interpreter i = new Interpreter();  // Construct an interpreter
		i.source(APPInterpreter.class.getResource("/somefile.bsh").getPath());
	}

}
