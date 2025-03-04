package individual.blog.users.dto;

import individual.blog.domain.entity.Blog;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
public class MyProfileDto {
    private String email;
    private String name;
    private LocalDateTime createAt;

    private List<Blog> blog;


}
