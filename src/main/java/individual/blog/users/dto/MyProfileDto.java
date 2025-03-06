package individual.blog.users.dto;

import individual.blog.domain.entity.Blog;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class MyProfileDto {
    private String email;
    private String name;
    private LocalDate createAt;

    private List<Blog> blog;


}
