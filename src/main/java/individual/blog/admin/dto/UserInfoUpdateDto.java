package individual.blog.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoUpdateDto {
    private Long id;
    private String email;
    private String name;
    private List<String> roles;
}
