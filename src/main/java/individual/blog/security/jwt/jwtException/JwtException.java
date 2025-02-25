package individual.blog.security.jwt.jwtException;

public class JwtException extends RuntimeException{
    public JwtException(String msg){
        super(msg);
    }
}
