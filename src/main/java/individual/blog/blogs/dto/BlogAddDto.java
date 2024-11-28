package individual.blog.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogAddDto {
    private String title;
    private String content;
    private String imgUrl;
    private String createAt;
}
