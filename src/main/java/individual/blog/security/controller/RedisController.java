package individual.blog.security.controller;

import individual.blog.security.jwt.repository.RefreshTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    private final RefreshTokenRepository refreshTokenRepository;

    public RedisController(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    // GET 요청으로 Redis에서 값 조회
    @GetMapping("/redis/get")
    public Object getRedisValue(@RequestParam String key) {
        return refreshTokenRepository.get(key);
    }


//    @RequestMapping("/api/refresh")
//    public
}
