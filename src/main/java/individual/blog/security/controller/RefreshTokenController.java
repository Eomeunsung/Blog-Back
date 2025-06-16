package individual.blog.security.controller;

import individual.blog.reponse.ResponseDto;
import individual.blog.security.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @RequestMapping("/api/refresh")
    public ResponseEntity<ResponseDto<?>> refreshToken(@RequestBody Map<String, String> tokenBody, @AuthenticationPrincipal UserDetails userDetails){
        String token = tokenBody.get("token");

        ResponseDto responseDto = refreshTokenService.refreshToken(token, userDetails);
        if(responseDto.getCode().equals("F200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }


    }
}
