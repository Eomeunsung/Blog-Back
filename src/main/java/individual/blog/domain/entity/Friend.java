package individual.blog.domain.entity;

import individual.blog.domain.enums.FriendStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
public class Friend implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private Account friend;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendStatus status;
}
