package individual.blog.admin.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserInfoDto {
    private Long id;
    private String email;
    private String name;
    private List<String> role;
    private LocalDate createAt;
}
