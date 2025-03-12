package individual.blog.users.dto;

import individual.blog.domain.enums.FriendStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendListGetDto {
    private Long id;
    private String email;
    private String name;
    private String status;
}
