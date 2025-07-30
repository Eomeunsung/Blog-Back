package individual.blog.exception.blogException.found;

import individual.blog.exception.CustomException;

public class BlogNotFoundException extends CustomException {
    public BlogNotFoundException() {
        super("B001", "블로그를 찾을 수 없습니다. 다시 시도해 주시기 바랍니다.");
    }

}
