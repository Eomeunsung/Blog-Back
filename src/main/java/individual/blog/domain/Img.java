package individual.blog.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name="blog_id", nullable = false)
    private Blog blog;

    @Column
    private String urlImg;


}
