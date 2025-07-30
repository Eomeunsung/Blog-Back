package individual.blog.Img;

import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Img;
import individual.blog.domain.repository.ImgRepository;
import individual.blog.exception.imgException.ImgCreateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImgWriteServiceImpl {

    private final ImgRepository imgRepository;

    public void writeImg(List<String> imgList, Blog blog){
        List<Img> imgs = new ArrayList<>();
        for (String url : imgList) {
            if(url==null){
                throw new ImgCreateException();
            }
            Img img = new Img();
            img.setUrlImg(url);
            img.setBlog(blog);
            imgs.add(img);
        }
        imgRepository.saveAll(imgs);
    }
}
