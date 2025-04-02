package individual.blog.users.controller;

import individual.blog.domain.repository.AccountRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.users.dto.NameDto;
import individual.blog.users.dto.SignInDto;
import individual.blog.users.dto.SignUpDto;
import individual.blog.users.service.UserService;
import individual.blog.util.CustomFileUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CustomFileUtil customFileUtil;

    @Value("${spring.blog.upload.path}")
    private String uploadPath;

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
    public ResponseEntity<ResponseDto<?>> myProfileUpdate(@RequestBody NameDto nameDto, @AuthenticationPrincipal UserDetails userDetails){
        ResponseDto responseDto = userService.myProfileUpdate(nameDto, userDetails);
        if(responseDto.getCode().equals("U200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/upload", consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> imgUpload(@RequestPart(value="files", required=false) MultipartFile file){
        if(file != null){
            try {
                String savedFile = customFileUtil.changeSaveFiles(file);
                ResponseDto responseDto = ResponseDto.setSuccess("200","이미지 업로드 성공", savedFile);
                return new ResponseEntity<>(responseDto, HttpStatus.OK);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
