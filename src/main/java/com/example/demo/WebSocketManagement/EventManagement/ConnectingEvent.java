package com.example.demo.WebSocketManagement.EventManagement;

import com.example.demo.Exception.ConnectedNullUser;
import com.example.demo.UnitModel.UserModel.UserStatus;
import com.example.demo.Service.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Component
public class ConnectingEvent {
    @Autowired
    private Account account;
    private final Map<String, String> sessionMap = new ConcurrentHashMap<>();
    private final Map<String, ReentrantLock> sessionLock = new ConcurrentHashMap<>();

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {
        log.info("СОЗДАНО ПОДКЛЮЧЕНИЕ");
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String id = (String) headerAccessor.getSessionAttributes().get("id");
        Thread.startVirtualThread(() -> {
            ReentrantLock thread = sessionLock.computeIfAbsent(sessionId, k -> new ReentrantLock());
            thread.lock();
            try {
                if (id == null) {
                    log.error("подключен пользователь без id \n {}", headerAccessor);
                    return;
                }
                sessionMap.putIfAbsent(sessionId, id);
                account.changeUserStatus(id, UserStatus.ONLINE);
                log.info("изменен статус пользователя на онлайн {}", id);
            } catch (Exception e) {
                log.error("Ошибка подключения пользователя {}, сессия {}", id, sessionId);
                throw new ConnectedNullUser(headerAccessor);
            } finally {
                thread.unlock();
            }
        });
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

            Thread.startVirtualThread(() -> {
                ReentrantLock thread = sessionLock.get(sessionId);

                if (thread != null) {

                thread.lock();
                try {
                    String id = sessionMap.remove(sessionId);
                    if (id == null) {
                        return;
                    }
                        account.changeUserStatus(id, UserStatus.OFFLINE);
                        log.info("изменен статус пользователя на оффлайн {}", id);
                } catch(NullPointerException id){
                        log.error("Отключен пользовательн без id \n {}", headerAccessor);
                    } finally {
                        thread.unlock();
                        sessionLock.remove(sessionId);
                    }
                } else {
                    String id = sessionMap.remove(sessionId);
                    if (id != null) {
                        account.changeUserStatus(id, UserStatus.OFFLINE);
                        log.warn("Пользователь перешел в оффлайн без лока {}", sessionId);
                    }
                }
            });
        }
    }
