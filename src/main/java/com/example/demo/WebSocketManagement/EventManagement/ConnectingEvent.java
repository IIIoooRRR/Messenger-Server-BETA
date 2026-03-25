package com.example.demo.WebSocketManagement.EventManagement;

import com.example.demo.UnitModel.UserModel.UserStatus;
import com.example.demo.UserManagemeng.Methods.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ConnectingEvent {
    @Autowired
    private Account account;
    private final ConcurrentHashMap<String, String> sessionMap = new ConcurrentHashMap<>();



    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        log.info("СОЗДАНО ПОДКЛЮЧЕНИЕ");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String id = (String) headerAccessor.getSessionAttributes().get("id");
        Thread.startVirtualThread(() -> {
            if (id == null) {
                log.error("подключен пользователь без id \n {}", headerAccessor);
                return;
            }

            sessionMap.putIfAbsent(sessionId, id);
            account.changeUserStatus(id, UserStatus.ONLINE);
            log.info("изменен статус пользователя на онлайн {}", id);
        });
    }
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        Thread.startVirtualThread(() -> {

            String id = sessionMap.remove(sessionId);
            if (id == null) {
                log.error("Отключен пользовательн без id \n {}", headerAccessor);
                return;
            }
            account.changeUserStatus(id, UserStatus.OFFLINE);
            log.info("изменен статус пользователя на оффлайн {}", id);
        });
    }
}
