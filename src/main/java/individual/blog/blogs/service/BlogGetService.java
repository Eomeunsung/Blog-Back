package individual.blog.blogs.service;

import individual.blog.blogs.dto.CommentGetDto;
import individual.blog.domain.entity.Comment;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.CommentRespository;
import individual.blog.reponse.ResponseDto;
import individual.blog.blogs.dto.BlogDetailDto;
import individual.blog.blogs.dto.BlogDto;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.entity.Blog;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Log4j2
@Service
public class BlogGetService {
    private final BlogRepository blogRepository;
    private final CommentRespository commentRespository;

    private final AccountRepository accountRepository;

    public BlogGetService(BlogRepository blogRepository, CommentRespository commentRespository, AccountRepository accountRepository) {
        this.blogRepository = blogRepository;
        this.commentRespository = commentRespository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public ResponseDto<List<BlogDto>> blogList() {
     
        try {
            List<Blog> blogOptional = blogRepository.findAllBy();
            log.info("리스트 "+blogOptional);
            if (blogOptional == null) {
                return ResponseDto.setFailed("000", "데이터 없음");
            }
            List<BlogDto> blogDtoList = new ArrayList<>();
            for (Blog blog : blogOptional) {
                BlogDto blogDto = new BlogDto();
                blogDto.setId(blog.getId());
                blogDto.setTitle(blog.getTitle());
                blogDto.setContent(blog.getContent());
//                blogDto.setImgUrl();
                blogDto.setLocalDate(blog.getCreateAt());
                blogDtoList.add(blogDto);
            }
            return ResponseDto.setSuccess("200", "list 조회성공", blogDtoList);
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
//            blogDetailDto.setImgUrl(imgOptional.get().getUrlImg());
            return ResponseDto.setSuccess("200", "상세 정보 조회 성공", blogDetailDto);
        } catch (Exception e){
            return ResponseDto.setFailed("000", "상세 정보 못 찾음");
        }
    }
}
