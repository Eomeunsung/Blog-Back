package individual.blog.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    // 댓글을 작성한 Account와의 관계
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false) // 외래 키 설정
    private Account account;

    // 댓글이 속한 Blog와의 관계
    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false) // 외래 키 설정
    private Blog blog;

    private String name;


    // 추가적인 내용, 예를 들어 댓글 내용, 작성일 등을 추가할 수 있습니다.
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDate createdAt;  // 생성 시간 등
}
