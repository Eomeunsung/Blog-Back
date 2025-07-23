package individual.blog.blogs.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface BlogDeleteService {

    ResponseEntity blogDelete(Long blogId, UserDetails userDetails);
}
