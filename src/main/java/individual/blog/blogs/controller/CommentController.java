package individual.blog.blogs.controller;

import individual.blog.blogs.dto.CommentWriteDto;
import individual.blog.blogs.service.CommentWriteService;
import individual.blog.reponse.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CommentController {

    private final CommentWriteService commentWriteService;
    @PostMapping("/comment")
    public ResponseEntity<ResponseDto<?>> commentWrite(@RequestBody CommentWriteDto commentWriteDto, @AuthenticationPrincipal UserDetails userDetails){
        ResponseDto responseDto = commentWriteService.commentWrite(commentWriteDto, userDetails);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
