package individual.blog.websocket.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ChatGroupCreateDto {
    private List<ChatUserIdDto> chatUserIdDtoList;

    private String groupName;
}
