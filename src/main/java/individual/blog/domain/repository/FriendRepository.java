package individual.blog.domain.repository;

import individual.blog.domain.entity.Friend;
import individual.blog.domain.enums.FriendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByAccount_Id(Long id);

    List<Friend> findByFriend_Id(Long id);

    Friend findByAccount_IdAndFriend_Id(Long accountId, Long friendId);

    List<Friend> findByAccount_IdOrFriend_IdAndStatus(Long accountId, Long accountId2, FriendStatus status);

}
