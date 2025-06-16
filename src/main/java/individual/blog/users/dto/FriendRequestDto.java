package individual.blog.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendRequestDto {
    Long accountId;
    Long friendId;
    String accountName;
    String FriendName;
}
