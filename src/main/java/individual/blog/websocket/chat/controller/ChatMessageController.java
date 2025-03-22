package individual.blog.websocket.chat.controller;

import individual.blog.websocket.chat.dto.ChatMessageDto;
import individual.blog.websocket.chat.enums.MessageTypeEnum;
import individual.blog.websocket.chat.service.ChatWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
@Slf4j
public class ChatMessageController {
    private final SimpMessagingTemplate messagingTemplate;

    private final ChatWriteService chatWriteService;

    public ChatMessageController(SimpMessagingTemplate messagingTemplate, ChatWriteService chatWriteService) {
        this.messagingTemplate = messagingTemplate;
        this.chatWriteService = chatWriteService;
    }

//    @MessageMapping("/chat")
//    @SendTo("/topic")
//    public ChatMessageDto sendMessage(ChatMessageDto chatMessageDto, @DestinationVariable Long roomId){
//        ChatMessageDto chatResponse =  new ChatMessageDto();
//        chatResponse.setContent(chatMessageDto.getContent());
//        chatResponse.setName(chatMessageDto.getName());
//        return chatResponse;
//    }

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(ChatMessageDto chatMessageDto, @DestinationVariable Long roomId){
        log.info("들어온 DTO "+chatMessageDto.getContent()+" 아이디 "+roomId +" 이름 "+chatMessageDto.getName());
        String destination = "/topic/chat/"+roomId;
        log.info("보낼 주소 "+destination);
        chatMessageDto.setType(MessageTypeEnum.CHAT);
        chatMessageDto.setCreateAt(LocalDate.now());
        chatWriteService.chatWriteService(chatMessageDto, roomId);
        messagingTemplate.convertAndSend(destination, chatMessageDto);

    }

}
