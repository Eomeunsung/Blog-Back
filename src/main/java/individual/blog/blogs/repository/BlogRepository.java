package individual.blog.blogs.repository;

import individual.blog.domain.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllBy();

    Optional<Blog> findById(Long id);
}
