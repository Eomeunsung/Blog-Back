package individual.blog.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {
    private Long id;
    private String name;

    public ChatRoomDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
