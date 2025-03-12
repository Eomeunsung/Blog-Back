package individual.blog.domain.repository;

import individual.blog.domain.entity.ChatRoom;
import individual.blog.domain.entity.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
