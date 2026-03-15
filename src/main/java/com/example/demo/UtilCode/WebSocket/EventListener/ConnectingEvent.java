package com.example.demo.UtilCode.WebSocket.EventListener;

import com.example.demo.UserCode.methods.UserStatusChange;
import com.example.demo.UserCode.UserModel.UserStatus;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Component
public class ConnectingEvent {
    @Autowired
    private UserStatusChange userStatusChange;
    private final static ExecutorService STATUS_EVENT_THREAD = Executors.newFixedThreadPool(20);
    @EventListener
    public void handleConnect(SessionConnectEvent event) {
        STATUS_EVENT_THREAD.submit(() -> {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            String id = headerAccessor.getFirstNativeHeader("id");
            if (id == null) {
                System.out.println("Подключился пользователь без id!!!");
                System.out.println("Место подключения:" + headerAccessor.getHost());
                System.out.println("Устройство: " + headerAccessor.getMessage());
            } else {

                userStatusChange.setUserStatus(id, UserStatus.ONLINE);
            }
        });
    }
    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        STATUS_EVENT_THREAD.submit(() -> {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
            String id = headerAccessor.getFirstNativeHeader("id");
            if (id == null) {

                System.out.println("Отключился пользователь без id");
                System.out.println("Место подключения:" + headerAccessor.getHost());
                System.out.println("Все данные подключения: " + headerAccessor.getMessage());
            } else {
                userStatusChange.setUserStatus(id, UserStatus.OFFLINE);
            }
        });
    }
    @PreDestroy
    private void cleanThrow() {
        STATUS_EVENT_THREAD.shutdown();
        try {
            if (!STATUS_EVENT_THREAD.awaitTermination(5, TimeUnit.SECONDS)) {
                STATUS_EVENT_THREAD.shutdownNow();
            }
        } catch (InterruptedException exception) {
            STATUS_EVENT_THREAD.shutdownNow();
            System.out.println("Не все задачи были выполнены в пуле потоков: " + Instant.now());
            Thread.currentThread().interrupt();
            System.out.println("Статус прерывания потока:" + Thread.currentThread().isInterrupted());
        }
    }
}
