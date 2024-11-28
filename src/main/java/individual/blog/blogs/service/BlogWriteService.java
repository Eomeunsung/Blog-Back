package individual.blog.blogs.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import individual.blog.Reponse.ResponseDto;
import individual.blog.blogs.dto.BlogAddDto;
import individual.blog.blogs.repostiory.BlogRepostiory;
import individual.blog.domain.Blog;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlogWriteService {

    private final BlogRepostiory blogRepostiory;

    @Transactional
    public ResponseDto<ResponseDto<BlogAddDto>> blogWirte(BlogAddDto blogAddDto){
        try{
            Blog blog = new Blog();
            blog.setTitle(blogAddDto.getTitle());
            blog.setContent(blogAddDto.getContent());
            blog.setUrlImg(blogAddDto.getImgUrl());
            blog.setCreateAt(LocalDateTime.now());
            blogRepostiory.save(blog);
            return ResponseDto.setSuccess("200", "글 작성 성공", null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("000", "글 작성 실패");
        }
    }
}
