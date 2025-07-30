package individual.blog.Img;

import individual.blog.domain.entity.Img;
import individual.blog.domain.repository.ImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeleteImg {
    private final ImgRepository imgRepository;

    public void deleteImg(List<Img> imgList){
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
}
