package individual.blog.domain.repository;

import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
