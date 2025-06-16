package individual.blog.websocket.chat.dto;

import individual.blog.websocket.chat.enums.MessageTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChatMessageDto {
    private String name;
    private String email;
    private String content;
    private LocalDate createAt;
    private MessageTypeEnum type;

}
