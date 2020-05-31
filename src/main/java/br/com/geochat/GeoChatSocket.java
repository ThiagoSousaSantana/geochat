package br.com.geochat;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


@ServerEndpoint(
    value = "/chat/{username}",
    encoders = {SetEncoder.class},
    decoders = {})         
@ApplicationScoped
public class GeoChatSocket {
    
    Logger log = LoggerFactory.getLogger(GeoChatSocket.class);
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        updateOnlineUsers();
        sessions.put(username, session);
        var joinMessage = "User: " + username + " joined";
        log.info(joinMessage);
        broadcast(joinMessage);
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
        updateOnlineUsers();
        var closeMessage = "User " + username + " left";
        log.info(closeMessage);
        broadcast(closeMessage);
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
        updateOnlineUsers();
        log.error("User " + username + " left on error: " + throwable, throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("username") String username) {
        broadcast(">> " + username + ": " + message);
    }

    private void updateOnlineUsers() {
        sessions.values().forEach(session -> {
            session.getAsyncRemote().sendObject(sessions.keySet(), result -> {
                if (result.getException() != null) {
                    log.error("Unable to send online users", result.getException());
                }
            });
        });
    }

    private void broadcast(String message) {
        sessions.values().forEach(session -> sendMessage(session, message));
    }

    private void sendMessage(Session session, String message) {
        session.getAsyncRemote().sendObject(message, result -> {
            if (result.getException() != null) {
                log.error("Unable to send message", result.getException());
            }
        });
    }
}