package individual.blog.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshDto {
    private Boolean tokenFlag;
    private String refreshToke;
}
