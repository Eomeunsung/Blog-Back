package individual.blog.blogs;

import individual.blog.Reponse.ResponseDto;
import individual.blog.blogs.dto.BlogAddDto;
import individual.blog.blogs.dto.BlogDetailDto;
import individual.blog.blogs.dto.BlogDto;
import individual.blog.blogs.service.BlogDeleteService;
import individual.blog.blogs.service.BlogGetService;
import individual.blog.blogs.service.BlogWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlogController {

    private final BlogGetService blogGetService;
    private final BlogWriteService blogWriteService;
    private final BlogDeleteService blogDeleteService;


    @GetMapping("/blog")
    public ResponseEntity<ResponseDto<List<BlogDto>>> blogGetAllList(){
        ResponseDto<List<BlogDto>> responseDto = blogGetService.blogList();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/blog/{blogId}")
    public ResponseEntity<ResponseDto<BlogDetailDto>> blogDetail(@PathVariable Long blogId){
        ResponseDto<BlogDetailDto> responseDto = blogGetService.blogDetail(blogId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/blog/write")
    public ResponseEntity<ResponseDto<BlogAddDto>> blogWrite(@RequestBody BlogAddDto blogAddDto){
        ResponseDto<BlogAddDto> responseDto = blogWriteService.blogWirte(blogAddDto).getData();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/blog/{blogId}")
    public ResponseEntity<ResponseDto<Object>> blogDelete(@PathVariable Long blogId){
        return blogDeleteService.blogDelete(blogId);
    }
}
