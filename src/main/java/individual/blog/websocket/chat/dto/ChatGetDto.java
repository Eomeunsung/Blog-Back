package individual.blog.websocket.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatGetDto {

    private Long roomId;
    private List<ChatMessageGetDto> chatMessageGetDtoList;
    private List<ChatUserProfileDto> userprofile;
}
