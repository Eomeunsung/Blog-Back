package individual.blog.websocket.chat.dto;

import individual.blog.domain.entity.ChatMessage;
import individual.blog.domain.enums.ChatType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ChatGetDto {

    private Long roomId;
    private List<ChatMessageGetDto> chatMessageGetDtoList;
}
