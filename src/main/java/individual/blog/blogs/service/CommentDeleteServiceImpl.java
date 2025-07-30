package individual.blog.blogs.service;

import individual.blog.domain.entity.Comment;
import individual.blog.domain.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CommentDeleteServiceImpl implements CommentDeleteService{

    private final CommentRepository commentRepository;

    @Override
    public Boolean commentDelete(Set<Comment> commentList) {
        for(Comment comment : commentList){
            if(comment!=null){
                commentRepository.delete(comment);
            }else {
                return false;
            }
        }
        return true;
    }
}
