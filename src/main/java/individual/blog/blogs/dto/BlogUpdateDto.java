package individual.blog.blogs.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BlogUpdateDto {
    private Long id;
    private String title;
    private String content;
    private List<String> imgUrl = new ArrayList<>();
    private String createAt;
}
