package individual.blog.exception.userException;

import individual.blog.domain.entity.Role;
import individual.blog.exception.userException.roleException.RoleException;
import individual.blog.reponse.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserGlobalException {

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<ResponseDto<?>> handleRoleException(RoleException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }
}
