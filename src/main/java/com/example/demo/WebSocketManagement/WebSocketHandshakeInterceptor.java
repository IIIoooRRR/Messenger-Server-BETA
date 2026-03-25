package com.example.demo.WebSocketManagement;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
@Component
@Slf4j
@NullMarked
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
            if (request instanceof ServletServerHttpRequest) {
                String id = ((ServletServerHttpRequest) request)
                                .getServletRequest()
                                .getParameter("id");
                if (id !=null) {
                    attributes.put("id", id);
                    log.info("Подключен пользователь с id {}", id);
                    return true;
                }
            }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {
        if (request instanceof ServletServerHttpRequest) {
            String id = ((ServletServerHttpRequest) request)
                            .getServletRequest()
                            .getParameter("id");
            log.info("Провалена сессия {}", id);
        }

    }
}
