package individual.blog.domain.repository;

import individual.blog.domain.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllBy();

    Optional<Blog> findById(Long id);

    List<Blog> findByAccount_Id(Long accountId);
}
