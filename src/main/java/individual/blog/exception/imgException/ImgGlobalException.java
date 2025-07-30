package individual.blog.exception.imgException;

import individual.blog.reponse.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImgGlobalException {

    @ExceptionHandler(ImgCreateException.class)
    public ResponseEntity<ResponseDto> handleImgCreateException(ImgCreateException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseDto.setFailed(ex.getErrorCode(), ex.getMessage()));
    }
}
