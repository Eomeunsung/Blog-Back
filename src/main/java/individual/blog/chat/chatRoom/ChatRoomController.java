package individual.blog.chat.chatRoom;

import individual.blog.chat.dto.ChatRoomDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    
    @PostMapping("/chatroom")
    public ResponseEntity<ChatRoomDto> createChatRoom(@RequestBody String name){
        ChatRoomDto newRoom = chatRoomService.createChatRoom(name);
        return new ResponseEntity<>(newRoom, HttpStatus.OK);
    }
    
    @GetMapping("/chatroom")
    public ResponseEntity<List<ChatRoomDto>> getChatRoom(){
        List<ChatRoomDto> chatRooms = chatRoomService.getChatRooms();
        return new ResponseEntity<>(chatRooms, HttpStatus.OK);
    }
}
