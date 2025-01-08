package individual.blog.blogs.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import individual.blog.Reponse.ResponseDto;
import individual.blog.blogs.dto.BlogAddDto;
import individual.blog.blogs.dto.BlogImgDto;
import individual.blog.blogs.repostiory.BlogRepostiory;
import individual.blog.blogs.repostiory.ImgRepostiory;
import individual.blog.domain.Blog;
import individual.blog.domain.Img;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlogWriteService {

    private final BlogRepostiory blogRepostiory;
    private final ImgRepostiory imgRepostiory;

    @Transactional
    public ResponseDto<ResponseDto<BlogAddDto>> blogWirte(BlogAddDto blogAddDto){
        try{

            Blog blog = new Blog();
            blog.setTitle(blogAddDto.getTitle());
            blog.setContent(blogAddDto.getContent());
            blog.setCreateAt(LocalDateTime.now());
            blogRepostiory.save(blog);
            if(blogAddDto.getImgUrl().length!=0){
                for(String imgs : blogAddDto.getImgUrl()){
                    Img img = new Img();
                    img.setUrlImg(imgs);
                    img.setBlog(blog);
                    imgRepostiory.save(img);
                }
            }
            return ResponseDto.setSuccess("200", "글 작성 성공", null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("000", "글 작성 실패");
        }
    }


}
