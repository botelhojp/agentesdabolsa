package agentesdabolsa.servlet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocket")
public class AppWebSocketServlet {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void onOpen(Session session) {
		logger.info("Connected ... " + session.getId());
		clients.add(session);
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		try {
			synchronized (clients) {
				for (Session client : clients) {
					if (!client.equals(session)) {
						client.getBasicRemote().sendText(message);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
		clients.remove(session);
	}

}
