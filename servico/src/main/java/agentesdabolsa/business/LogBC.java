package agentesdabolsa.business;

import java.net.URI;

import agentesdabolsa.servlet.WebsocketClientEndpoint;

public class LogBC {

	private static WebsocketClientEndpoint clientEndPoint;

	public static void log(String log) {
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
			System.err.println("InterruptedException exception: " + ex.getMessage());
		}
	}
}
