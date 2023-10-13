package com.example.vn_railway.config.web_socket;

import com.example.vn_railway.common.ws.TemporaryStorageWebSocket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getTemporaryStorageWebSocket(),"/data");
    }
    @Bean
    public TemporaryStorageWebSocket getTemporaryStorageWebSocket(){
        return new TemporaryStorageWebSocket();
    }
}
