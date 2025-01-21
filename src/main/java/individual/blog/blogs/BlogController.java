package individual.blog.blogs;

import individual.blog.Reponse.ResponseDto;
import individual.blog.blogs.dto.BlogAddDto;
import individual.blog.blogs.dto.BlogDetailDto;
import individual.blog.blogs.dto.BlogDto;
import individual.blog.blogs.dto.BlogUpdateDto;
import individual.blog.blogs.service.BlogDeleteService;
import individual.blog.blogs.service.BlogGetService;
import individual.blog.blogs.service.BlogUpdateService;
import individual.blog.blogs.service.BlogWriteService;
import individual.blog.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
public class BlogController {
    @Value("${spring.blog.upload.path}")
    private String uploadPath;

    private final BlogGetService blogGetService;
    private final BlogWriteService blogWriteService;
    private final BlogDeleteService blogDeleteService;
    private final CustomFileUtil customFileUtil;
    private final BlogUpdateService blogUpdateService;


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

    @PutMapping("/blog")
    public ResponseEntity<ResponseDto<Object>> blogUpdate(@RequestBody BlogUpdateDto blogUpdateDto){
        log.info("블로그 아이이디 "+blogUpdateDto.getId());
        ResponseDto<Object> responseDto = blogUpdateService.blogUpdate(blogUpdateDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> imgUpload(@RequestPart(value="files", required=false) List<MultipartFile> files){
        List<String> savedFilesDeatil = new ArrayList<>();
        if(files != null){
            try {
                savedFilesDeatil = customFileUtil.saveFiles(files);
                if(!savedFilesDeatil.isEmpty()){
                    List<String> fullFileDeatils = savedFilesDeatil.stream()
                            .map(fileName -> uploadPath+"/"+fileName)
                            .collect(Collectors.toList());
                    ResponseDto responseDto = ResponseDto.setSuccess("200","이미지 업로드 성공", fullFileDeatils);
                    return new ResponseEntity<>(responseDto, HttpStatus.OK);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
