package individual.blog.blogs.service;

import individual.blog.Img.ImgWriteServiceImpl;
import individual.blog.blogs.dto.BlogUpdateDto;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.exception.blogException.BlogGetException;
import individual.blog.exception.userException.roleException.RoleException;
import individual.blog.reponse.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class BlogUpdateService {
    private final BlogRepository blogRepository;
    private final ImgWriteServiceImpl imgWriteService;

    @Transactional
    public ResponseEntity<ResponseDto> blogUpdate(BlogUpdateDto blogUpdateDto, UserDetails userDetails) {
        Long id = blogUpdateDto.getId();

        Blog blog = blogRepository.findById(id).orElseThrow(BlogGetException::new);

        if(!blog.getAccount().getEmail().equals(userDetails.getUsername())){
            throw new RoleException();
        }

        blog.setTitle(blogUpdateDto.getTitle());
        blog.setContent(blogUpdateDto.getContent());
        blogRepository.save(blog);

        if (blogUpdateDto.getImgUrl() != null && !blogUpdateDto.getImgUrl().isEmpty()) {
            imgWriteService.writeImg(blogUpdateDto.getImgUrl(), blog);

        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseDto.setSuccess("200", "글 수정 성공", null));

    }

}
