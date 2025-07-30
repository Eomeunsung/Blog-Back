package individual.blog.exception.blogException;

import individual.blog.exception.CustomException;

public class BlogGetException extends CustomException {
    public BlogGetException() {
        super("B000", "블로그를 찾을 수 없습니다.");
    }
}
