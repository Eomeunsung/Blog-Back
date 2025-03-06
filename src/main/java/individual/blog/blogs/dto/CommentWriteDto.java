package individual.blog.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentWriteDto {
    Long blogId;
    String content;
}
