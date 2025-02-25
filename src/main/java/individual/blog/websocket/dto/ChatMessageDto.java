package individual.blog.websocket.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDto {
    // 메시지  타입 : 입장, 채팅, 퇴장
    public enum MessageType{
        JOIN, TALK, LEAVE, ADMIN
    }
    private String name;
    private MessageType messageType; // 메시지 타입
    private Long chatRoomId; // 방번호
    private String message; // 메시지
}
