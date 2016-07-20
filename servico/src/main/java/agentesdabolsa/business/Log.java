package agentesdabolsa.business;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;

import agentesdabolsa.servlet.WebsocketClientEndpoint;

public class Log {

	private static WebsocketClientEndpoint clientEndPoint;
	
	private static boolean local = false;

	public static void info(String log) {
		if (local){
			System.out.println(log);
			return;
		}
		try {
			// open websocket
			if (clientEndPoint == null){
				clientEndPoint = new WebsocketClientEndpoint(new URI("ws://localhost:8080/agentesdabolsa/websocket"));
			}

			// add listener
			clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
				public void handleMessage(String message) {
					System.out.println(message);
				}
			});
			// send message to websocket
			clientEndPoint.sendMessage(log);


		} catch (Exception ex) {
			//System.err.println("InterruptedException exception: " + ex.getMessage());
		}
	}

	public static void configure(boolean value) {
		local = value;
		
	}

	public static void error(Throwable e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		sw.toString();
		Log.info("\n"+ sw.toString());
	}
}
