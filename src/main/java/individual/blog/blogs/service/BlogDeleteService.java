package individual.blog.blogs.service;

import individual.blog.reponse.ResponseDto;
import individual.blog.blogs.repository.BlogRepository;
import individual.blog.blogs.repository.ImgRepository;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Img;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class BlogDeleteService {
    private final BlogRepository blogRepository;
    private final ImgRepository imgRepostiory;


    @Transactional
    public ResponseEntity blogDelete(Long blogId) {
        try {
            // Blog 조회
            Blog blog = blogRepository.findById(blogId)
                    .orElseThrow(() -> new IllegalArgumentException("Blog 못 찾음"));

            // Img 조회 및 삭제
            List<Img> imgList = imgRepostiory.findByBlog_Id(blogId);
            if (!imgList.isEmpty()) {
                for (Img img : imgList) {
                    String url = img.getUrlImg(); // ex: "http://localhost:8080/Img/example.jpg"
                    String filePath = url.replace("http://localhost:8080/", "Img/"); // "Img/example.jpg"
                    log.info("삭제할 이름 "+filePath);
                    Path path = Path.of(filePath);
                    try{
                        Files.deleteIfExists(path);
                        imgRepostiory.delete(img);
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

        } catch (IllegalArgumentException e) {
            // Blog 찾지 못했을 때
            ResponseDto responseDto = ResponseDto.setFailed("404", "Blog를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);

        } catch (Exception e) {
            // 기타 삭제 실패 처리
            ResponseDto responseDto = ResponseDto.setFailed("500", "Blog 삭제 실패");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
        }
    }
}
