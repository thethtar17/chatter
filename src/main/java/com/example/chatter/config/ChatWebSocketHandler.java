//package com.example.chatter.config;
//import org.slf4j.*;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//import org.springframework.web.socket.CloseStatus;
//
////import ch.qos.logback.classic.Logger;
//
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketMessage;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//public class ChatWebSocketHandler extends TextWebSocketHandler {
//    private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
//    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // Add session to the map (with a unique identifier if needed)
//        sessions.put(session.getId(), session);
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        // Remove session from the map
//        sessions.remove(session.getId());
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // Handle incoming messages (e.g., broadcast to all sessions)
//        String payload = message.getPayload();
//        for (WebSocketSession s : sessions.values()) {
//            s.sendMessage(new TextMessage(payload));
//        }
//    }
//}
//
//
