package individual.blog.exception.userException.roleException;

import individual.blog.exception.CustomException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

public class RoleException extends CustomException {
    public RoleException() {
        super("U000", "권한이 없습니다.");
    }
}
