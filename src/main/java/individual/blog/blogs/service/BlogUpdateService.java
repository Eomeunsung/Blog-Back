package individual.blog.blogs.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.blogs.dto.BlogUpdateDto;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.repository.ImgRepository;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Img;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class BlogUpdateService {
    private final BlogRepository blogRepository;
    private final ImgRepository imgRepostiory;
    private final AccountRepository accountRepository;

    @Transactional
    public ResponseDto<Object> blogUpdate(BlogUpdateDto blogUpdateDto, UserDetails userDetails) {
        try{
            Long id = blogUpdateDto.getId();
            Optional<Blog> blogOptional = blogRepository.findById(id);

            blogOptional.orElseThrow(() -> new IllegalArgumentException("블로그 정보를 찾을 수 없습니다."));

            Account account = accountRepository.findByEmail(userDetails.getUsername());
            if(account==null){
                return ResponseDto.setFailed("500", "유저 정보를 찾을 수 없습니다. 다시 로그인 해주십시오");
            }

            Blog blog = blogOptional.get();

            List<Blog> account_Id = blogRepository.findByAccount_Id(account.getId());

            Boolean flag = false;
            if(account_Id.isEmpty()){
                return ResponseDto.setFailed("500", "이 블로그는 수정할 수 있는 권한이 없습니다.");
            }else{
                for(Blog blog1 : account_Id){
                    if(blog1.getId() == blog.getId()){
                        flag = true;
                        break;
                    }
                }
            }
            if(!flag){
                return ResponseDto.setFailed("500", "이 블로그는 수정할 수 있는 권한이 없습니다.");
            }

            blog.setTitle(blogUpdateDto.getTitle());
            blog.setContent(blogUpdateDto.getContent());
            blogRepository.save(blog);


            if (blogUpdateDto.getImgUrl() != null && !blogUpdateDto.getImgUrl().isEmpty()) {
                List<Img> imgs = new ArrayList<>();
                for (String url : blogUpdateDto.getImgUrl()) {
                    Img img = new Img();
                    img.setUrlImg(url);
                    img.setBlog(blog);
                    imgs.add(img);
                }
                imgRepostiory.saveAll(imgs);
            }
            return ResponseDto.setSuccess("200", "글 수정 성공", null);
        }catch (Exception e){
            return ResponseDto.setFailed("500", "글 수정 다시 해주십시오");
        }
    }

}
