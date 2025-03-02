package individual.blog.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Data
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

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "account_blog", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
            @JoinColumn(name = "blog_id") })
    @ToString.Exclude
    private Set<Blog> blog = new HashSet<>();

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "account_roles", joinColumns = { @JoinColumn(name = "account_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    @ToString.Exclude
    private Set<Role> userRoles = new HashSet<>();

}
