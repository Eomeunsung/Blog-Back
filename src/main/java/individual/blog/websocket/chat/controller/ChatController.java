package individual.blog.websocket.chat.controller;

import individual.blog.domain.entity.Account;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.websocket.chat.dto.ChatGroupCreateDto;
import individual.blog.websocket.chat.dto.NameUpdateDto;
import individual.blog.websocket.chat.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@AllArgsConstructor
@Slf4j
public class ChatController {
    private final AccountRepository accountRepository;

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

    @GetMapping("/create/private/{id}")
    private ResponseEntity<ResponseDto<?>> chatPrivateCreate(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        log.info("채팅방 생성 "+userDetails.getUsername()+" "+id);
        if(userDetails.getUsername()==null){
            ResponseDto responseDto = ResponseDto.setFailed("001", "사용자가 없습니다. 다시 로그인 해주시기 바랍니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        if(id==null){
            ResponseDto responseDto = ResponseDto.setFailed("002", "상대방이 없습니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
        ResponseDto responseDto = chatService.chatPrivateCreate(id, userDetails);
        if (responseDto.getCode().equals("200") || responseDto.getCode().equals("201")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{accountId}")
    private ResponseEntity<ResponseDto<?>> chatGet(@PathVariable Long accountId, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails==null){
            ResponseDto responseDto = ResponseDto.setFailed("001", "사용자가 없습니다. 다시 로그인 해주시기 바랍니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
        Account account = accountRepository.findByEmail(userDetails.getUsername());

        ResponseDto responseDto = chatService.chatPrivateGet(accountId, account.getId());
        if (responseDto.getCode().equals("200") || responseDto.getCode().equals("001")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/msg/{id}")
    public ResponseEntity<ResponseDto<?>> chatMessageGet(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        if (userDetails==null){
            ResponseDto responseDto = ResponseDto.setFailed("001", "사용자가 없습니다. 다시 로그인 해주시기 바랍니다.");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

        ResponseDto responseDto = chatService.chatMessageGet(id);
        if (responseDto.getCode().equals("200") || responseDto.getCode().equals("001")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create/group")
    public ResponseEntity<ResponseDto<?>> chatGroupCreate(@RequestBody ChatGroupCreateDto chatGroupCreateDto, @AuthenticationPrincipal UserDetails userDetails){
        log.info("들어온 그룹 이름 "+chatGroupCreateDto.getGroupName()+" "+chatGroupCreateDto.getChatUserIdDtoList());
        ResponseDto responseDto = chatService.createGroupChat(chatGroupCreateDto, userDetails);
        if (responseDto.getCode().equals("200") || responseDto.getCode().equals("001")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/name/update")
    public ResponseEntity<ResponseDto<?>> chatUpdateName(@RequestBody NameUpdateDto nameUpdateDto, @AuthenticationPrincipal UserDetails userDetails){
        ResponseDto responseDto = chatService.updateName(nameUpdateDto, userDetails);
        if (responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
