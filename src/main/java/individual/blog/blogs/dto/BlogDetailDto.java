package individual.blog.blogs.dto;

import individual.blog.domain.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BlogDetailDto {
    private Long blogId;
    private String title;
    private String content;
    private Set<CommentGetDto> comments;
}
