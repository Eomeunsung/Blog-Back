package individual.blog.blogs.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.blogs.dto.BlogAddDto;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.repository.ImgRepository;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Img;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlogWriteService {

    private final BlogRepository blogRepository;
    private final ImgRepository imgRepostiory;
    private final AccountRepository accountRepository;

    @Transactional
    public ResponseDto<ResponseDto<BlogAddDto>> blogWirte(BlogAddDto blogAddDto, User user){
        try{

            Blog blog = new Blog();
            blog.setTitle(blogAddDto.getTitle());
            blog.setContent(blogAddDto.getContent());
            blog.setCreateAt(LocalDateTime.now());
            log.info("이미지 "+blogAddDto.getImgUrl());
            blogRepository.save(blog);
            if(blogAddDto.getImgUrl()!=null && !blogAddDto.getImgUrl().isEmpty() ){
                List<Img> imgList = new ArrayList<>();
                for(String url : blogAddDto.getImgUrl()){
                    Img img = new Img();
                    img.setUrlImg(url);
                    img.setBlog(blog);
                    imgList.add(img);
                }
                imgRepostiory.saveAll(imgList);
            }
            Account account = accountRepository.findByEmail(user.getUsername());
            Set<Blog> blogSet = new HashSet<>();
            blogSet.add(blog);
            account.setBlog(blogSet);
            accountRepository.save(account);
            return ResponseDto.setSuccess("200", "글 작성 성공", null);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseDto.setFailed("000", "글 작성 실패");
        }
    }


}
