package individual.blog.blogs.service;

import individual.blog.blogs.dto.CommentWriteDto;
import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Comment;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.repository.CommentRepository;
import individual.blog.reponse.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CommentWriteService {

    private final AccountRepository accountRepository;

    private final CommentRepository commentRepository;

    private final BlogRepository blogRepository;
    @Transactional
    public ResponseDto<?> commentWrite(CommentWriteDto commentWriteDto, UserDetails userDetails){
        try{
            Account account = accountRepository.findByEmail(userDetails.getUsername());
            if(account==null){
                return ResponseDto.setFailed("C000", "로그인 해주시기 바랍니다.");
            }

            Long blogId = commentWriteDto.getBlogId();
            log.info("블로그 아이디 "+commentWriteDto.getBlogId()+" 내용 "+commentWriteDto.getContent());

            Optional<Blog> blogOptional = blogRepository.findById(blogId);

            if(blogOptional.isEmpty()){
                return ResponseDto.setFailed("C001", "블로그 정보가 없습니다.");
            }
            Blog blog = blogOptional.get();

            Comment comment = new Comment();
            comment.setAccount(account);
            comment.setName(account.getName());
            comment.setBlog(blog);
            comment.setContent(commentWriteDto.getContent());
            comment.setCreatedAt(LocalDate.now());
            commentRepository.save(comment);
            return ResponseDto.setSuccess("200", "댓글 작성 성공");
        }catch (Exception e){
            return ResponseDto.setFailed("C002", "알 수 없는 오류가 발생했습니다.");
        }
    }
}
