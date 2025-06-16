package individual.blog.blogs.controller;

import individual.blog.reponse.ResponseDto;
import individual.blog.blogs.dto.BlogAddDto;
import individual.blog.blogs.dto.BlogDetailDto;
import individual.blog.blogs.dto.BlogListDto;
import individual.blog.blogs.dto.BlogUpdateDto;
import individual.blog.blogs.service.BlogDeleteService;
import individual.blog.blogs.service.BlogGetService;
import individual.blog.blogs.service.BlogUpdateService;
import individual.blog.blogs.service.BlogWriteService;
import individual.blog.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/blog")
public class BlogController {
    @Value("${spring.blog.upload.path}")
    private String uploadPath;

    private final BlogGetService blogGetService;
    private final BlogWriteService blogWriteService;
    private final BlogDeleteService blogDeleteService;
    private final CustomFileUtil customFileUtil;
    private final BlogUpdateService blogUpdateService;


    @GetMapping("/list")
    public ResponseEntity<ResponseDto<List<BlogListDto>>> blogGetAllList(){
        ResponseDto<List<BlogListDto>> responseDto = blogGetService.blogList();
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<ResponseDto<BlogDetailDto>> blogDetail(@PathVariable Long blogId){
        ResponseDto<BlogDetailDto> responseDto = blogGetService.blogDetail(blogId);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/write")
    public ResponseEntity<ResponseDto<BlogAddDto>> blogWrite(@RequestBody BlogAddDto blogAddDto, @AuthenticationPrincipal UserDetails userDetails){

        log.info("쓰기 dto "+blogAddDto.getTitle()+" "+blogAddDto.getContent());
        ResponseDto responseDto = blogWriteService.blogWrite(blogAddDto, userDetails);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<ResponseDto<Object>> blogDelete(@PathVariable Long blogId, @AuthenticationPrincipal UserDetails userDetails){
        log.info("삭제할 아이디 "+blogId);
        return blogDeleteService.blogDelete(blogId, userDetails);
    }

    @PutMapping("")
    public ResponseEntity<ResponseDto<Object>> blogUpdate(@RequestBody BlogUpdateDto blogUpdateDto, @AuthenticationPrincipal UserDetails userDetails){
        log.info("블로그 아이이디 "+blogUpdateDto.getId());
        ResponseDto<Object> responseDto = blogUpdateService.blogUpdate(blogUpdateDto, userDetails);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> imgUpload(@RequestPart(value="files", required=false) List<MultipartFile> files){
        log.info("서버에서 받은 이미지 "+files);
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
