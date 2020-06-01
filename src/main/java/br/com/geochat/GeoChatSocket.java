package br.com.geochat;

import java.util.LinkedList;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import br.com.geochat.internal.AbstractMessageEncoder;
import br.com.geochat.internal.ListEncoder;
import br.com.geochat.internal.MessageDecoder;
import br.com.geochat.internal.MessageEncoder;
import br.com.geochat.internal.UserEncoder;
import br.com.geochat.models.AbstractMessage;
import br.com.geochat.models.Message;
import br.com.geochat.models.MessageType;
import br.com.geochat.models.User;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;


@ServerEndpoint(
    value = "/chat/{username}/{avatar}",
    encoders = {
        ListEncoder.class,
        MessageEncoder.class,
        AbstractMessageEncoder.class,
        UserEncoder.class
    },
    decoders = {MessageDecoder.class})         
@ApplicationScoped
public class GeoChatSocket {
    
    Logger log = LoggerFactory.getLogger(GeoChatSocket.class);
    LinkedList<User> sessions = new LinkedList<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("avatar") String avatar) {
        sessions.add(new User(username, avatar, session));
        broadcastOnlineUsers();
        log.info("User: " + username + " joined");
    }

    @OnClose
    public void onClose(Session session, @PathParam("username") String username) {
        sessions.remove(new User(username));
        broadcastOnlineUsers();
        
        log.info("User " + username + " left");
    }

    @OnError
    public void onError(Session session, @PathParam("username") String username, Throwable throwable) {
        sessions.remove(new User(username));
        broadcastOnlineUsers();
        log.error("User " + username + " left on error: " + throwable, throwable);
    }

    @OnMessage
    public void onMessage(Message message, @PathParam("username") String username) {
        if (message.isPrivateMessage()) {
            sendMessage(
                sessions.get(
                    sessions.indexOf(new User(username))).getSession(),
                     message);
        } else {
            broadcast(message);
        }
    }

    private void broadcastOnlineUsers() {
        sessions.forEach(user -> {
            sendOnlineUsers(user.getSession());
        });
    }

    private void broadcast(Message message) {
        sessions.forEach(user -> sendMessage(user.getSession(), message));
    }

    private void sendOnlineUsers(Session session) {
        session.getAsyncRemote().sendObject(new AbstractMessage(MessageType.USER_LIST, sessions), result -> {
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