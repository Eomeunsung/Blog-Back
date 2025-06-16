package individual.blog.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoDto {
    String name;
    String email;
    String accessToken;
    String refreshToken;
}
