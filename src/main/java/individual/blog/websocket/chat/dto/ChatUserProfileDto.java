package individual.blog.websocket.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatUserProfileDto {
    private Long id;
    private String name;
    private String imgURl;
}
