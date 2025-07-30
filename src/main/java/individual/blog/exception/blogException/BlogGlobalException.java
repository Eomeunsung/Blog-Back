package individual.blog.exception.blogException;

import individual.blog.exception.blogException.delete.DeleteException;
import individual.blog.exception.blogException.found.BlogNotFoundException;
import individual.blog.reponse.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BlogGlobalException {

    @ExceptionHandler(BlogGetException.class)
    public ResponseEntity<ResponseDto> handleBlogGetException(BlogGetException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<ResponseDto<?>> handleBlogDeleteException(DeleteException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(BlogNotFoundException.class)
    public ResponseEntity<ResponseDto<?>> handleBlogNotFoundException(BlogNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }
}
