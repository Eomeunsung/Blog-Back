package individual.blog.blogs.service;

import individual.blog.blogs.dto.CommentGetDto;
import individual.blog.domain.entity.Comment;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.CommentRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.blogs.dto.BlogDetailDto;
import individual.blog.blogs.dto.BlogListDto;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.entity.Blog;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlogGetService {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    private final AccountRepository accountRepository;


    @Transactional
    public ResponseDto<List<BlogListDto>> blogList() {
     
        try {
            List<Blog> blogOptional = blogRepository.findAllBy();
            log.info("리스트 "+blogOptional);
            if (blogOptional == null) {
                return ResponseDto.setFailed("000", "데이터 없음");
            }
            List<BlogListDto> blogListDtoList = new ArrayList<>();
            for (Blog blog : blogOptional) {
                BlogListDto blogListDto = new BlogListDto();
                blogListDto.setId(blog.getId());
                blogListDto.setTitle(blog.getTitle());
                blogListDto.setContent(blog.getContent());
                blogListDto.setUserName(blog.getAccount().getName());
//                blogDto.setImgUrl();
                blogListDto.setLocalDate(blog.getCreateAt());
                blogListDtoList.add(blogListDto);
            }
            return ResponseDto.setSuccess("200", "list 조회성공", blogListDtoList);
        } catch (Exception e) {
            return ResponseDto.setFailed("000", "데이터 없음");
        }
    }

    @Transactional
    public ResponseDto<BlogDetailDto> blogDetail(Long id){
        try{
            Optional<Blog> blogOptional = blogRepository.findById(id);
            blogOptional.orElseThrow(()-> new IllegalArgumentException("상세 정보를 못 찾음"));
//            Optional<Img> imgOptional = imgRepostiory.findByBlog_Id(id);
//            imgOptional.orElseThrow(()-> new IllegalArgumentException("이미지가 없음"));
            Blog blog = blogOptional.get();
            log.info("블로그 디테일 "+blog.getContent());
            BlogDetailDto blogDetailDto = new BlogDetailDto();
            blogDetailDto.setBlogId(blog.getId());
            blogDetailDto.setTitle(blog.getTitle());
            blogDetailDto.setContent(blog.getContent());

            if(!blog.getComments().isEmpty()){
                Set<CommentGetDto> comments = new HashSet<>();
                for(Comment comment : blog.getComments()){
                    Comment com = new Comment();
                    CommentGetDto commentGetDto = new CommentGetDto();
                    commentGetDto.setId(comment.getId());
                    commentGetDto.setName(comment.getName());
                    commentGetDto.setContent(comment.getContent());
                    commentGetDto.setAccountId(comment.getBlog().getId());
                    commentGetDto.setCreateAt(comment.getCreatedAt());

                    comments.add(commentGetDto);
                }
                blogDetailDto.setComments(comments);
            }
            return ResponseDto.setSuccess("200", "상세 정보 조회 성공", blogDetailDto);
        } catch (Exception e){
            return ResponseDto.setFailed("000", "상세 정보 못 찾음");
        }
    }
}
