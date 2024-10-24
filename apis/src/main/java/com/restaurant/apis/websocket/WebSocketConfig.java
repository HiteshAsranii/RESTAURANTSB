package com.restaurant.apis.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");

        registry.setApplicationDestinationPrefixes("/app");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //this serves as the starting point of all our websockets connections.
        registry.addEndpoint("/websocket").setAllowedOriginPatterns("*").withSockJS();
    }
    

}

// @Configuration: Indicates that this class provides Spring Bean configuration.
// @EnableWebSocketMessageBroker: Enables WebSocket message handling, backed by a message broker.
// configureMessageBroker: Configures a message broker to handle message routing.
// enableSimpleBroker: Enables a simple in-memory message broker to send messages to connected clients. The /topic prefix indicates that messages sent to destinations starting with /topic will be routed to the broker.
// setApplicationDestinationPrefixes: Sets the prefix for message destinations sent from clients to the application. Here, messages sent to destinations starting with /app will be routed to controller methods.
// registerStompEndpoints: Registers a STOMP over WebSocket endpoint for clients to connect. The withSockJS() method enables SockJS fallback options for clients that don't support WebSocket directly.
