package individual.blog.blogs.service;

import individual.blog.Reponse.ResponseDto;
import individual.blog.blogs.repostiory.BlogRepostiory;
import individual.blog.domain.Blog;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogDeleteService {
    private final BlogRepostiory blogRepostiory;

    @Transactional
    public ResponseEntity blogDelete(Long blogId){
        try{
            Optional<Blog> blogOptional = blogRepostiory.findById(blogId);
            blogOptional.orElseThrow(()->new IllegalArgumentException("Blog 못 찾음"));
            blogRepostiory.deleteById(blogId);
            ResponseDto responseDto = ResponseDto.setSuccess("200","Blog 삭제 성공", null);
            return ResponseEntity.ok(responseDto);
        }catch (Exception e){
            ResponseDto responseDto = ResponseDto.setFailed("000", "Blog 삭제 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}
