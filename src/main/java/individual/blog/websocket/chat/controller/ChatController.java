package individual.blog.websocket.chat.controller;

import individual.blog.reponse.ResponseDto;
import individual.blog.websocket.chat.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/list")
    private ResponseEntity<ResponseDto<?>> chatRoomList(@AuthenticationPrincipal UserDetails userDetails){
        ResponseDto responseDto = chatService.chatRoomList(userDetails);
        if (responseDto.getCode().equals("200") || responseDto.getCode().equals("201")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
