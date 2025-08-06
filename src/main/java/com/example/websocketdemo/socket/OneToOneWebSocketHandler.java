package com.example.websocketdemo.socket;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OneToOneWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    // Import statements
    // import org.slf4j.Logger;
    // import org.slf4j.LoggerFactory;

    // Logger instance for secure logging
//     private static final Logger logger = LoggerFactory.getLogger(YourClassName.class);

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String userId = getUserId(session);
        if (userId != null) {
            userSessions.put(userId, session);
//            System.out.println("Connected to " + userId);
            logger.info("User connected: {}", userId);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // JSON: {"to":"bob", "message":"Hi Bob!"}
        String payload = message.getPayload();
        // import org.json.JSONObject
        // JSONObject is used for parsing and handling JSON data
        JSONObject json = new JSONObject(payload);
        String to = json.getString("to");
        String msg = json.getString("message");

        WebSocketSession receiverSession = userSessions.get(to);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.sendMessage(new TextMessage("From " + getUserId(session) + ": " + msg));
        } else {
            session.sendMessage(new TextMessage("User " + to + " is not available"));
        }
    }

    // Import statements for logging
    /*
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    These imports are necessary to use the SLF4J logging framework, which provides a more flexible and configurable logging solution compared to System.out.println().
    */
    private static final Logger logger = LoggerFactory.getLogger(WebSocketHandler.class);

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String userId = getUserId(session);
        if (userId != null) {
            userSessions.remove(userId);
//            System.out.println("User closed: " + userId);
            logger.info("{} disconnected", userId);
        }
    }






    private String getUserId(WebSocketSession session) {
        // Expect ?userId=bob in the connection URI
        String uri = session.getUri().toString();
        if (uri.contains("userId=")) {
            return uri.substring(uri.indexOf("userId=") + 7);
        }
        return null;
    }
}

