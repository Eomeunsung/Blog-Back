package individual.blog.websocket.chat.controller;

import individual.blog.websocket.chat.dto.ChatMessageDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatMessageController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/sub/{roomId}")
    public ChatMessageDto sendMessage(ChatMessageDto chatMessageDto, @DestinationVariable Long roomId){
        ChatMessageDto chatResponse =  new ChatMessageDto();
        chatResponse.setContent(chatMessageDto.getContent());
        chatResponse.setName(chatMessageDto.getName());
        return chatResponse;
    }

}
