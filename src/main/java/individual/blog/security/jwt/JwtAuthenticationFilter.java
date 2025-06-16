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
import org.springframework.http.HttpStatus;
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
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"JWT에러\", \"message\":\"" + e.getMessage() + "\"}");
                log.info("JWT 에러1 "+e.getMessage());
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
                    log.info("ContextHolder 저장된 유저 "+SecurityContextHolder.getContext().getAuthentication().getName());
                }
            } catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"error\":\"JWT에러\", \"message\":\"" + e.getMessage() + "\"}");
                log.info("JWT 에러2 "+e.getMessage());
                return;
            } catch (Exception e) {
                // 기타 예외 처리
                response.setStatus(HttpServletResponse.SC_FORBIDDEN); //500 에러
                response.getWriter().write("JWT Error");
                log.info("JWT 에러3 "+e.getMessage());
                return;
            }

        }
        filterChain.doFilter(request, response);
    }

}
