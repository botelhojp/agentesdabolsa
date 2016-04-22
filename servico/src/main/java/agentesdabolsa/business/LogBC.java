package agentesdabolsa.business;

import java.util.ArrayList;
import java.util.List;

public class LogBC {
	
	private static List<String> messages = new ArrayList<String>();
	
	public static void log(String log) {
		messages.add(log);
	}

	public static List<String> getMessages() {
		ArrayList<String> rt = new ArrayList<String>();
		rt.addAll(0,messages);
		messages.clear();
		return rt;
	}

}
