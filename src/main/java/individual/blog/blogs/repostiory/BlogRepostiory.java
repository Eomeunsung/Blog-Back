package individual.blog.blogs.repostiory;

import individual.blog.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepostiory extends JpaRepository<Blog, Long> {
    List<Blog> findAllBy();

    Optional<Blog> findById(Long id);
}
