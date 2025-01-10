package individual.blog.blogs.repostiory;

import individual.blog.domain.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImgRepostiory extends JpaRepository<Img, Long> {
    List<Img> findByBlog_Id(Long id);
}
