package individual.blog.users.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountGetDto {
    private Long id;
    private String email;
    private String name;
}
