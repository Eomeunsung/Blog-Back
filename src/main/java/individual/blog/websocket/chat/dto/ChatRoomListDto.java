package individual.blog.websocket.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChatRoomListDto {

    private Long id;
    private String name;
    private String content;
    private LocalDate createAt;
    private String type;
}
