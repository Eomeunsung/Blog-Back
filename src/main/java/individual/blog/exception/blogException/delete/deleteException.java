package individual.blog.exception.blogException.delete;

import individual.blog.exception.CustomException;

public class deleteException extends CustomException {
    public deleteException() {
        super("D000", "블로그를 삭제 실패했습니다.");
    }
}
