package individual.blog.blogs.service;

import individual.blog.blogs.dto.CommentWriteDto;
import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Comment;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.repository.CommentRepository;
import individual.blog.exception.blogException.BlogGetException;
import individual.blog.reponse.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CommentWriteServiceImpl {

    private final AccountRepository accountRepository;

    private final CommentRepository commentRepository;

    private final BlogRepository blogRepository;
    @Transactional
    public ResponseEntity<ResponseDto> commentWrite(CommentWriteDto commentWriteDto, UserDetails userDetails){

        Account account = accountRepository.findByEmail(userDetails.getUsername());
        Long blogId = commentWriteDto.getBlogId();
        Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if(blogOptional.isEmpty()){
            throw new BlogGetException();
        }
        Blog blog = blogOptional.get();

        Comment comment = Comment
                .builder()
                .account(account)
                .name(account.getName())
                .blog(blog)
                .content(commentWriteDto.getContent())
                .createdAt(LocalDate.now())
                .build();
        commentRepository.save(comment);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("200", "댓글 작성 성공"));
    }
}
