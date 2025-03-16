package individual.blog.websocket.chat.dto;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.ChatRoom;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ChatMessageGetDto {

    private Long id;

    private ChatRoom chatRoom;

    private String name;

    private String email;

    private String content;

    private LocalDate createAt;

}
