package individual.blog.domain.entity;

import individual.blog.domain.enums.ChatType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ChatRoomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name="chatRoom_id", nullable = false)
    private ChatRoom chatRoom;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChatType type;
}
