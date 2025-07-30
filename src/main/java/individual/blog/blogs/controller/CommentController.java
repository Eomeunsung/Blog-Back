package individual.blog.blogs.controller;

import individual.blog.blogs.dto.CommentWriteDto;
import individual.blog.blogs.service.CommentWriteServiceImpl;
import individual.blog.reponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
public class CommentController {

    private final CommentWriteServiceImpl commentWriteService;
    @PostMapping("/comment")
    public ResponseEntity<ResponseDto> commentWrite(@RequestBody CommentWriteDto commentWriteDto, @AuthenticationPrincipal UserDetails userDetails){
        return commentWriteService.commentWrite(commentWriteDto, userDetails);
    }
}
