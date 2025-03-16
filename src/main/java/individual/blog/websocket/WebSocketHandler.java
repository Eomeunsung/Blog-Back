package individual.blog.websocket;

import individual.blog.security.jwt.JwtUtil;
import individual.blog.websocket.chat.dto.ChatMessageDto;
import individual.blog.websocket.chat.enums.MessageTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        log.info("들어온 메시지 "+event);
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());

        //클라이언트에서 보낸 jwt 토큰 가져오기
        String token = accessor.getFirstNativeHeader("Authorization");
        String email = accessor.getFirstNativeHeader("email");
        String id = accessor.getFirstNativeHeader("id");
        String name = accessor.getFirstNativeHeader("name");

        if(token==null || !token.startsWith("Bearer ")){
            log.error("토큰이 제공되지않음");
            return;
        }

        token = token.substring(7);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);


        if(!jwtUtil.isValidateToken(token, userDetails)){
            throw new IllegalArgumentException("토큰 만료 다시 로그인 바랍니다.");
        }

        log.info("[SessionConnected]: nickname = " + name);

        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setName(name);
        chatMessageDto.setContent(name+" 님이 참가하였습니다.");
        chatMessageDto.setType(MessageTypeEnum.SYSTEM);

        messagingTemplate.convertAndSend(String.format("/topic/chat/%s", id), chatMessageDto);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String token = accessor.getFirstNativeHeader("Authorization");
        String email = accessor.getFirstNativeHeader("email");
        String id = accessor.getFirstNativeHeader("id");
        String name = accessor.getFirstNativeHeader("name");

        if(token==null || !token.startsWith("Bearer ")){
            log.error("토큰이 제공되지않음");
            return;
        }

        token = token.substring(7);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);


        if(!jwtUtil.isValidateToken(token, userDetails)){
            throw new IllegalArgumentException("토큰 만료 다시 로그인 바랍니다.");
        }

        log.info("[SessionDisconnected]: "+name);

        // 시스템 메시지를 생성하고 발행합니다.
        ChatMessageDto chatMessageDto = new ChatMessageDto();
        chatMessageDto.setName(name);
        chatMessageDto.setContent(name+" 님이 나갔습니다.");
        chatMessageDto.setType(MessageTypeEnum.SYSTEM);

        messagingTemplate.convertAndSend(String.format("/topic/chat/%s", id), chatMessageDto);
    }

}
