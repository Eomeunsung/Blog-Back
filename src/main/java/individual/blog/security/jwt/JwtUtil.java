package individual.blog.security.jwt;


import individual.blog.security.jwt.jwtException.JwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtUtil {


    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key key;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }


    public String createToken(UserDetails userDetails){
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(userDetails))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30분
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String refreshCreateToken(UserDetails userDetails){
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(userDetails))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)) // 7일
                .signWith(key, signatureAlgorithm)
                .compact();
    }



    // JWT 토큰에서 사용자 이름을 추출하는 메서드
    public String extractUser(String token){
        return extractClaims(token, Claims::getSubject);
    }

    // JWT 토큰에서 클레임을 추출하는 메서드
    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // JWT 토큰에서 모든 클레임을 추출하는 메서드
    private Claims extractAllClaims(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            throw new JwtException("Expired JWT token");
        }

    }

    // 토큰 만료 여부를 확인하는 메서드
    private Boolean isTokenExpired(String token){

        return extractExpired(token).before(new Date());
    }

//    // 토큰 10분 전 여부를 확인하는 메서드 (10분 전 기준)
//    private Boolean tokenExpired10Minutes(String token) {
//        // 현재 시간에서 10분 전의 시간 생성
//        Date tenMinutesLater = new Date(System.currentTimeMillis() + (1000 * 60 * 10));
//
//        // 만료 시간이 10분 후보다 이전인지 확인
//        return extractExpired(token).before(tenMinutesLater);
//    }

    // 토큰에서 만료 시간을 추출하는 메서드
    public Date extractExpired(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    // 토큰 유효성 검사 메서드
    private Boolean validateToken(String token, UserDetails userDetails){
        final String email = extractUser(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isValidateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = extractAllClaims(token);
            if (claims != null) {
                return validateToken(token, userDetails);  // 유효성 검사를 한 뒤 그 결과를 반환합니다.
            }
        }  catch (SecurityException | MalformedJwtException e){
            throw new JwtException("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e){
            throw new JwtException("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e){
            throw new JwtException("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e){
            throw new JwtException("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        } catch (io.jsonwebtoken.security.SignatureException e){
            throw new JwtException("Invalid JWT signature: {}");
        } catch (Exception e) {
            throw new JwtException("JWT Error"+e.getMessage());
        }
        return false;
    }

    private Map<String, Object> createHeader(){
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        return header;
    }
    private Map<String, Object> createClaims(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUsername());
        log.info("권항 "+userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return claims;
    }



}
