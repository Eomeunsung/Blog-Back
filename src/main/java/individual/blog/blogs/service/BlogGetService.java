package individual.blog.blogs.service;

import individual.blog.Reponse.ResponseDto;
import individual.blog.blogs.dto.BlogDetailDto;
import individual.blog.blogs.dto.BlogDto;
import individual.blog.blogs.dto.BlogGetTitleDto;
import individual.blog.blogs.repostiory.BlogRepostiory;
import individual.blog.domain.Blog;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class BlogGetService {
    private final BlogRepostiory blogRepostiory;

    public BlogGetService(BlogRepostiory blogRepostiory) {
        this.blogRepostiory = blogRepostiory;
    }

    @Transactional
    public ResponseDto<List<BlogDto>> blogList() {
     
        try {
            List<Blog> blogOptional = blogRepostiory.findAllBy();
            log.info("리스트 "+blogOptional);
            if (blogOptional == null) {
                return ResponseDto.setFailed("000", "데이터 없음");
            }
            List<BlogDto> blogDtoList = new ArrayList<>();
            for (Blog blog : blogOptional) {
                BlogDto blogDto = new BlogDto();
                blogDto.setId(blog.getId());
                blogDto.setTitle(blog.getTitle());
                blogDto.setContent(blog.getContent());
                blogDto.setImgUrl(blog.getUrlImg());
                blogDtoList.add(blogDto);
            }
            return ResponseDto.setSuccess("200", "list 조회성공", blogDtoList);
        } catch (Exception e) {
            return ResponseDto.setFailed("000", "데이터 없음");
        }
    }

    @Transactional
    public ResponseDto<BlogDetailDto> blogDetail(Long id){
        try{
            Optional<Blog> blogOptional = blogRepostiory.findById(id);
            blogOptional.orElseThrow(()-> new IllegalArgumentException("상세 정보를 못 찾음"));
            BlogDetailDto blogDetailDto = new BlogDetailDto();
            blogDetailDto.setTitle(blogOptional.get().getTitle());
            blogDetailDto.setContent(blogOptional.get().getContent());
            blogDetailDto.setImgUrl(blogOptional.get().getUrlImg());
            return ResponseDto.setSuccess("200", "상세 정보 조회 성공", blogDetailDto);
        } catch (Exception e){
            return ResponseDto.setFailed("000", "상세 정보 못 찾음");
        }
    }
}
