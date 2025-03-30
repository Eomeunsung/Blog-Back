package individual.blog.users.controller;

import individual.blog.reponse.ResponseDto;
import individual.blog.users.service.FriendService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friend")
    public ResponseEntity<ResponseDto<?>> friendsGet(@AuthenticationPrincipal UserDetails userDetails){
        ResponseDto responseDto = friendService.friendGet(userDetails);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    //일단 안쓰는거
    @GetMapping("/searchFriend")
    public ResponseEntity<ResponseDto<?>> searchFriends(@RequestParam(value = "search", defaultValue = "")String search){
        ResponseDto responseDto = friendService.searchFriend(search);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    //친구 추천 조회
    @GetMapping("/search")
    public ResponseEntity<ResponseDto<?>> search(@AuthenticationPrincipal UserDetails userDetails){
        ResponseDto responseDto = friendService.search(userDetails);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }


    //친구 추가 요청
    @PostMapping("friendAdd/{id}")
    public ResponseEntity<ResponseDto<?>> friendAdd(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        log.info("들어온 친구 추가 "+id);
        ResponseDto responseDto = friendService.friendAdd(id, userDetails);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/friendProfile/{id}")
    public ResponseEntity<ResponseDto<?>> friendProfile(@PathVariable Long id){
        ResponseDto responseDto = friendService.friendProfile(id);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    //요청 받은 친구 조회
    @GetMapping("/friendRequest")
    public ResponseEntity<ResponseDto<?>> friendRequest(@AuthenticationPrincipal UserDetails userDetails){
        ResponseDto responseDto = friendService.friendRequest(userDetails);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/friendAccept/{id}")
    public ResponseEntity<ResponseDto<?>> friendAccept(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        log.info("들어온 아이디 "+id+" 유저 "+userDetails.getUsername());
        ResponseDto responseDto = friendService.friendAccept(id, userDetails);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("friendDelete/{id}")
    public ResponseEntity<ResponseDto<?>> friendDelete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        ResponseDto responseDto = friendService.friendDelete(id, userDetails);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
