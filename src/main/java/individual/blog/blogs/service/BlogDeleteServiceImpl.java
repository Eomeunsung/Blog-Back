package individual.blog.blogs.service;

import individual.blog.Img.DeleteImg;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Img;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.repository.ImgRepository;
import individual.blog.exception.blogException.found.BlogNotFoundException;
import individual.blog.exception.userException.roleException.RoleException;
import individual.blog.reponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogDeleteServiceImpl implements BlogDeleteService {
    private final BlogRepository blogRepository;
    private final ImgRepository imgRepository;
    private final DeleteImg deleteImg;


    @Override
    @Transactional
    public ResponseEntity<ResponseDto> blogDelete(Long blogId, UserDetails userDetails) {
        log.info("들어온 삭제 서비스 아이디 "+blogId);

        // Blog 조회
        Optional<Blog> blogFind = blogRepository.findById(blogId);

        if(blogFind.isEmpty()){
            throw new BlogNotFoundException();
        }
        Blog blog = blogFind.get();

        if(!blog.getAccount().getEmail().equals(userDetails.getUsername())){
            throw new RoleException();
        }

        // Img 조회 및 삭제
        List<Img> imgList = imgRepository.findByBlog_Id(blogId);
        if (!imgList.isEmpty()) {
            deleteImg.deleteImg(imgList);
        }
        // Blog 삭제
        blogRepository.deleteById(blogId);

        // 성공 응답 반환
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("200", "Blog 삭제 성공", null));

    }


}
