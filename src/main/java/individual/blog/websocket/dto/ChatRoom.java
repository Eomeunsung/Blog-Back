package individual.blog.websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoom {
    public enum MessageType{
        CREATE, OUT
    }
    private Long chatRoomId;   // 채팅방 ID
    private String roomName;

}
