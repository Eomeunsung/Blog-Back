package individual.blog.security.jwt.repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private ValueOperations<String, Object> valueOperations;
    private final long REFRESH_EXPIRE = 60 * 60 * 24 * 7; // 7일

    @PostConstruct
    public void init(){
        valueOperations = redisTemplate.opsForValue();
    }

    public void save(String email, String refreshToken){
        valueOperations.set("refreshToken:"+email, refreshToken, REFRESH_EXPIRE, TimeUnit.SECONDS);
    }

    public String get(String email){
        return (String) valueOperations.get("refreshToken:"+email);
    }


    public void delete(String email){
        redisTemplate.delete("refreshToken:"+email);
    }

}
