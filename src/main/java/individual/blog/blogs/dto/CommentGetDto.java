package individual.blog.blogs.dto;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.Blog;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentGetDto {
    Long id;
    Long accountId;
    String email;
    String name;
    String content;
    LocalDate createAt;

}
