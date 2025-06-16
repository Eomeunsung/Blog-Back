package individual.blog.users.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpDto {
    private String email;
    private String name;
    private String password;
    private List<String> roles;
}
