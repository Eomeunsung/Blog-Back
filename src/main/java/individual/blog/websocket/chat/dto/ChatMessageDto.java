package individual.blog.websocket.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private String name;
    private String content;
    private String type;

}
