package individual.blog.users.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.service.annotation.GetExchange;

import java.time.LocalDate;

@Data
@Getter
@Setter
public class MyProfileDto {
    private String email;
    private String name;

}
