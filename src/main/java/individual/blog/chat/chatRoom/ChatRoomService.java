package individual.blog.chat.chatRoom;

import individual.blog.chat.dto.ChatRoomDto;
//import individual.blog.websocket.dto.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ChatRoomService {
    private List<ChatRoomDto> chatRooms = new ArrayList<>();
    private final AtomicLong roomId = new AtomicLong(1);

    public ChatRoomDto createChatRoom(String name) {
        ChatRoomDto chatRoomDto = new ChatRoomDto(roomId.getAndIncrement(), name);
        chatRooms.add(chatRoomDto);
        return chatRoomDto;
    }

    public List<ChatRoomDto> getChatRooms() {
        return chatRooms;
    }
}
