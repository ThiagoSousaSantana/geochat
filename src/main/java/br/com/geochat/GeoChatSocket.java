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

import br.com.geochat.internal.AbstractMessageEncoder;
import br.com.geochat.internal.MessageDecoder;
import br.com.geochat.internal.MessageEncoder;
import br.com.geochat.internal.SetEncoder;
import br.com.geochat.models.AbstractMessage;
import br.com.geochat.models.Message;
import br.com.geochat.models.MessageType;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


@ServerEndpoint(
    value = "/chat/{username}",
    encoders = {
        SetEncoder.class,
        MessageEncoder.class,
        AbstractMessageEncoder.class
    },
    decoders = {MessageDecoder.class})         
@ApplicationScoped
public class GeoChatSocket {
    
    Logger log = LoggerFactory.getLogger(GeoChatSocket.class);
    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        sessions.put(username, session);
        broadcastOnlineUsers();
        log.info("User: " + username + " joined");
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(username);
        broadcastOnlineUsers();
        
        log.info("User " + username + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(username);
        broadcastOnlineUsers();
        log.error("User " + username + " left on error: " + throwable, throwable);
    }

    @OnMessage
    public void onMessage(Message message, @PathParam("username") String username) {
        if (message.isPrivateMessage()) {
            sendMessage(sessions.get(message.getTo()), message);
        } else {
            broadcast(message);
        }
    }

    private void broadcastOnlineUsers() {
        sessions.values().forEach(session -> {
            sendOnlineUsers(session);
        });
    }

    private void broadcast(Message message) {
        sessions.values().forEach(session -> sendMessage(session, message));
    }

    private void sendOnlineUsers(Session session) {
        session.getAsyncRemote().sendObject(new AbstractMessage(MessageType.USER_LIST, sessions.keySet()), result -> {
            if (result.getException() != null) {
                log.error("Unable to send online users", result.getException());
            }
        });
    }

    private void sendMessage(Session session, Message message) {
        session.getAsyncRemote().sendObject(new AbstractMessage(MessageType.MESSAGE, message), result -> {
            if (result.getException() != null) {
                log.error("Unable to send message", result.getException());
            }
        });
    }
}