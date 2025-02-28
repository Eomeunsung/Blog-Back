package individual.blog.security.jwt;

import individual.blog.reponse.ResponseDto;
import individual.blog.security.service.CustomUserDetailService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailService userDetailService;

    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                jwt = authorizationHeader.substring(7);
                email = jwtUtil.extractUser(jwt);

            }catch (Exception e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value()); //401
                response.getWriter().write(e.getMessage());
                return;
            }
        }
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try{
                UserDetails userDetails = this.userDetailService.loadUserByUsername(email);
                if(jwtUtil.isValidateToken(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("token 토큰 "+usernamePasswordAuthenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (JwtException e) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value()); //401
                response.getWriter().write(e.getMessage());
            } catch (Exception e) {
                // 기타 예외 처리
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); //500 에러
                response.getWriter().write("JWT Error");
            }

        }
        filterChain.doFilter(request, response);
    }

}
