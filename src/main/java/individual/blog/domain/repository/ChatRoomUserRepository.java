package individual.blog.domain.repository;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.ChatRoomUser;
import individual.blog.domain.enums.ChatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {
    List<ChatRoomUser> findByAccount_Id(Long accountId);

    Optional<ChatRoomUser> findByAccountIdAndChatRoomIdAndType(Long accountId, Long chatRoomId, ChatType type);

    List<ChatRoomUser> findByAccountIdAndType(Long accountId, ChatType type);

    List<ChatRoomUser> findByChatRoom_id(Long id);
}
