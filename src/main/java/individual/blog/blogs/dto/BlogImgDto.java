package individual.blog.blogs.dto;

import individual.blog.domain.Blog;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogImgDto {
    private Blog blog;
    private String imgUrl;
}
