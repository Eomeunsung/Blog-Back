package individual.blog.users.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
@Setter
public class ProfileDto {
    private String email;
    private String name;
    private String imgUrl;
    private LocalDate createAt;
    private List<ProfileBlogDto> blogData;

}
