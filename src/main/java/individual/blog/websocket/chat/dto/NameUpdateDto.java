package individual.blog.websocket.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameUpdateDto {
    private Long id;
    private String name;
}
