package individual.blog.blogs.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class BlogDto {
    private Long id;
    private String title;
    private String content;
    private String imgUrl;
    private LocalDate localDate;
}
