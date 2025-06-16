package individual.blog.security.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.security.dto.RefreshDto;
import individual.blog.security.jwt.JwtUtil;
import individual.blog.security.jwt.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RefreshTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private final AccountRepository accountRepository;

    public RefreshTokenService(JwtUtil jwtUtil, RefreshTokenRepository refreshTokenRepository, AccountRepository accountRepository) {
        this.jwtUtil = jwtUtil;
        this.refreshTokenRepository = refreshTokenRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public ResponseDto<RefreshDto> refreshToken(String token, UserDetails userDetails){
        try{
            RefreshDto refreshDto = new RefreshDto();
            Account account = accountRepository.findByEmail(userDetails.getUsername());
            if(account==null){
                return ResponseDto.setFailed("F001", "다시 로그인 해주시기 바랍니다.");
            }
            boolean tokenFlag = tokenExpired10Minutes(token);
            if(!tokenFlag){
                refreshDto.setTokenFlag(false);
                refreshDto.setRefreshToke("");
            }else{
                String refreshToken = refreshTokenRepository.get(userDetails.getUsername());
                String refreshToken2 = jwtUtil.refreshCreateToken(userDetails);
                refreshTokenRepository.save(userDetails.getUsername(), refreshToken2);
                refreshDto.setTokenFlag(true);
                refreshDto.setRefreshToke(refreshToken);
            }


            return ResponseDto.setSuccess("F200","토큰",refreshDto);
        }catch (Exception e){
            return ResponseDto.setFailed("F002",e.getMessage());

        }
    }

    private Boolean tokenExpired10Minutes(String token) {
        // 현재 시간에서 10분 전의 시간 생성
        Date tenMinutesLater = new Date(System.currentTimeMillis() + (1000 * 60 * 10));
        // 만료 시간이 10분 전보다 이전인지 확인
        return jwtUtil.extractExpired(token).before(tenMinutesLater);
    }
}
