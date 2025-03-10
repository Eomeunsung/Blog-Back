package individual.blog.domain.repository;

import individual.blog.domain.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByAccount_Id(Long id);
}
