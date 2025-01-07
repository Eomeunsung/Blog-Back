package individual.blog.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class CustomFileUtil {

    @Value("${spring.blog.upload.path}")
    private String uploadPath;

    @PostConstruct
    public void init() {
        File file = new File(uploadPath);
        if(file.exists()==false){
            file.mkdirs();
        }
        uploadPath = file.getAbsolutePath();
        log.info("------------------");
        log.info("uploadPath: {}", uploadPath);
        log.info("------------------");
    }

    public List<String> saveFiles(MultipartFile[] files) throws IOException {
        if(files ==null || files.length==0){
            return null;
        }
        List<String> uploadNames = new ArrayList<>();
        for(MultipartFile file : files){
            String saveName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
            Path savePath = Paths.get(uploadPath, saveName);
            try{
                Files.copy(file.getInputStream(), savePath);
                uploadNames.add(saveName);
            }catch (IOException e){
                throw new IOException("파일 저장 중 오류 발생: " + file.getOriginalFilename(), e);
            }
        }
        return uploadNames;
    }
}
