package individual.blog.blogs.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BlogGetTitleDto {
    private Long id;
    private String title;
}
