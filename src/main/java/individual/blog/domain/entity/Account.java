package individual.blog.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column
    String email;

    @Column
    String name;

    @Column
    String password;

    @Column
    private LocalDate createAt;

    @Column
    private String profileImg;

    // 1:N 관계에서 외래 키는 Blog 테이블에만 존재하며,
    // Account 엔티티의 필드에서만 관계를 관리합니다.
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    @JsonIgnore // 무한 참조 방지
    @ToString.Exclude // 무한 참조 방지
    private Set<Blog> blogs = new HashSet<>();

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "account_roles", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    @JsonIgnore // 무한 참조 방지
    @ToString.Exclude // 무한 참조 방지
    private Set<Role> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "account")
    @JsonIgnore // 무한 참조 방지
    @ToString.Exclude // 무한 참조 방지
    private Set<Comment> comments = new HashSet<>();



}
