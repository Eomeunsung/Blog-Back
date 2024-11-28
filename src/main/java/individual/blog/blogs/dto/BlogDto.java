package individual.blog.blogs.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BlogDto {
    private Long id;
    private String title;
    private String content;
    private String imgUrl;
}
