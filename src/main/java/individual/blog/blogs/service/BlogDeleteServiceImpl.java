package individual.blog.blogs.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Comment;
import individual.blog.domain.entity.Img;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.repository.CommentRepository;
import individual.blog.domain.repository.ImgRepository;
import individual.blog.exception.blogException.found.BlogNotFoundException;
import individual.blog.exception.userException.roleException.RoleException;
import individual.blog.reponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogDeleteServiceImpl implements BlogDeleteService {
    private final BlogRepository blogRepository;
    private final ImgRepository imgRepository;
    private final AccountRepository accountRepository;
    private final CommentRepository commentRepository;


    @Override
    @Transactional
    public ResponseEntity blogDelete(Long blogId, UserDetails userDetails) {
        log.info("들어온 삭제 서비스 아이디 "+blogId);

        // Blog 조회
        Optional<Blog> blogFind = blogRepository.findById(blogId);

        if(blogFind.isEmpty()){
            throw new BlogNotFoundException();
        }
        Blog blog = blogFind.get();

        List<Comment> commentList = commentRepository.findByBlog_Id(blog.getId());

        if(!commentList.isEmpty()){
            for(Comment comment : commentList){
                commentRepository.delete(comment);
            }
        }

        Account ac = blog.getAccount();
        if(ac==null){
           throw new RoleException();
        }



        // Img 조회 및 삭제
        List<Img> imgList = imgRepository.findByBlog_Id(blogId);
        if (!imgList.isEmpty()) {
            for (Img img : imgList) {
                String url = img.getUrlImg(); // ex: "http://localhost:8080/Img/example.jpg"
                String filePath = url.replace("http://localhost:8080/", "Img/"); // "Img/example.jpg"
                log.info("삭제할 이름 "+filePath);
                Path path = Path.of(filePath);
                try{
                    if(Files.exists(path)){
                        Files.deleteIfExists(path);
                    }
                    if(img!=null){
                        imgRepository.delete(img);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        // Blog 삭제
        blogRepository.deleteById(blogId);

        // 성공 응답 반환
        ResponseDto responseDto = ResponseDto.setSuccess("200", "Blog 삭제 성공", null);
        return ResponseEntity.ok(responseDto);

    }


}
