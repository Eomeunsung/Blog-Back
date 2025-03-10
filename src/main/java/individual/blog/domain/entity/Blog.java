package individual.blog.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
public class Blog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate createAt;

    @ManyToOne
    @JoinColumn(name = "account_id")  // 이 외래 키 컬럼을 명시적으로 지정
    private Account account;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL)
    @JsonIgnore  // 이 필드는 직렬화할 때 제외
    private Set<Comment> comments = new HashSet<>();

}
