package individual.blog.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserInfoDetailDto {
    private String email;
    private String name;
    private LocalDate createAt;
    private List<String> roles;
}
