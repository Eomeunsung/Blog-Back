package individual.blog.users.controller;

import individual.blog.reponse.ResponseDto;
import individual.blog.users.dto.NameDto;
import individual.blog.users.dto.SignInDto;
import individual.blog.users.dto.SignUpDto;
import individual.blog.users.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Log4j2
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<?>> signUp(@RequestBody SignUpDto signUpDto){
        log.info("들어온 회원가입 데이터 "+ signUpDto.getEmail()+" "+ signUpDto.getName()+" "+ signUpDto.getPassword());
        ResponseDto responseDto = userService.create(signUpDto);
        if (responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<?>> login(@RequestBody SignInDto signInDto){
        ResponseDto responseDto = userService.signIn(signInDto);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/myprofile")
    public ResponseEntity<ResponseDto<?>> myProfile(@AuthenticationPrincipal UserDetails userDetails){
        ResponseDto responseDto = userService.myProfile(userDetails);
        if(responseDto.getCode().equals("200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/myprofile")
    public ResponseEntity<ResponseDto<?>> myProfileUpdate(@RequestBody NameDto nameDto, @AuthenticationPrincipal User user){
        ResponseDto responseDto = userService.myProfileUpdate(nameDto, user);
        if(responseDto.getCode().equals("U200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
