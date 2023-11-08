package Cook.Cookify_SpringBoot.websocket;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Log
@Component
@ServerEndpoint(value="/notify", configurator = ServerEndpointConfigurator.class)
public class NotifyWS {
    private static int onlineCount = 0;
    public static Set<Session> subscribers = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        onlineCount++;
        subscribers.add(session);
        log.info("OnOpen:" + onlineCount);
    }

    @OnClose
    public void OnClose(Session session) {
        onlineCount--;
        subscribers.remove(session);
        log.info("OnClose:" + onlineCount);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("OnMessage:" + message);
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.warning("onError:" + throwable.getMessage());
        subscribers.remove(session);
        onlineCount--;
    }

    public static void broadcast(String message) {
        try {
            for (Session session : subscribers) {
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
