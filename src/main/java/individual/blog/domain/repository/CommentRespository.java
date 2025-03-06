package individual.blog.domain.repository;

import individual.blog.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRespository extends JpaRepository<Comment, Long> {
}
