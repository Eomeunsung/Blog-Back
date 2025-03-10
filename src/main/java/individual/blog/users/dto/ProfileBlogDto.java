package individual.blog.users.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProfileBlogDto {
    private Long id;
    private String title;
    private LocalDate createAt;
}
