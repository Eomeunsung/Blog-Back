package individual.blog.blogs.repostiory;

import individual.blog.domain.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImgRepostiory extends JpaRepository<Img, Long> {
    Optional<Img> findByBlog_Id(Long id);
}
