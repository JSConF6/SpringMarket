package com.spring.market.config;

import com.spring.market.handler.WebSocketAlarmHandler;
import com.spring.market.handler.WebSocketChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket // 웹소켓 활성화
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketAlarmHandler webSocketAlarmHandler;
    private final WebSocketChatHandler webSocketChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketAlarmHandler, "ws/alarm").setAllowedOrigins("*")
        .addInterceptors(new HttpSessionHandshakeInterceptor());

        registry.addHandler(webSocketChatHandler, "ws/chat/**").setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
