package individual.blog.blogs.service;

import individual.blog.Reponse.ResponseDto;
import individual.blog.blogs.dto.BlogUpdateDto;
import individual.blog.blogs.repostiory.BlogRepostiory;
import individual.blog.blogs.repostiory.ImgRepostiory;
import individual.blog.domain.Blog;
import individual.blog.domain.Img;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class BlogUpdateService {
    private final BlogRepostiory blogRepostiory;
    private final ImgRepostiory imgRepostiory;

    @Transactional
    public ResponseDto<Object> blogUpdate(BlogUpdateDto blogUpdateDto) {
        try{
            Long id = blogUpdateDto.getId();
            Optional<Blog> blogOptional = blogRepostiory.findById(id);
            blogOptional.orElseThrow(() -> new IllegalArgumentException("블로그 정보를 못찾음"));
            Blog blog = blogOptional.get();
            blog.setTitle(blogUpdateDto.getTitle());
            blog.setContent(blogUpdateDto.getContent());
            blogRepostiory.save(blog);


            if (blogUpdateDto.getImgUrl() != null && !blogUpdateDto.getImgUrl().isEmpty()) {
                List<Img> imgs = new ArrayList<>();
                for (String url : blogUpdateDto.getImgUrl()) {
                    Img img = new Img();
                    img.setUrlImg(url);
                    img.setBlog(blog);
                    imgs.add(img);
                }
                imgRepostiory.saveAll(imgs);
            }
            return ResponseDto.setSuccess("200", "글 작성 성공", null);
        }catch (Exception e){
            return ResponseDto.setFailed("000", "글 작성 실패");
        }
    }

}
