package com.example.demo.Exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

@Slf4j
public class ConnectedNullUser extends RuntimeException {
    public ConnectedNullUser(StompHeaderAccessor stompHeaderAccessor) {
        super("Был подключен пользователь без id");
        log.error("Данные подключения: \n {}", stompHeaderAccessor);
    }
}
