package individual.blog.blogs.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class BlogListDto {
    private Long id;
    private String title;
    private String content;
    private LocalDate localDate;
    private String userName;
}
