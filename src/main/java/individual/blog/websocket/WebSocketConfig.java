package individual.blog.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); //발행자가 보낸 메시지를 가공할 필요없이 구독자들에게 바로 경로로 보냄
        registry.setApplicationDestinationPrefixes("/app"); //발행자가 보낸 메시지를 /app 로 보내 가공을 해서 /topic 경로로 구독자들에게 보냄
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 커넥션을 맺는 경로 설정. 만약 WebSocket을 사용할 수 없는 브라우저라면 다른 방식을 사용하도록 설정
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

}
