package individual.blog.blogs.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BlogAddDto {
    private String title;
    private String content;
    private List<String> imgUrl = new ArrayList<>();
    private String createAt;
}
