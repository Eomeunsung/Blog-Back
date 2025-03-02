package individual.blog.domain.repository;

import individual.blog.domain.entity.Img;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImgRepository extends JpaRepository<Img, Long> {
    List<Img> findByBlog_Id(Long id);
}
