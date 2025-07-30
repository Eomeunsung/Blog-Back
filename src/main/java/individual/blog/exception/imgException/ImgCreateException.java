package individual.blog.exception.imgException;

import individual.blog.exception.CustomException;

public class ImgCreateException extends CustomException {
    public ImgCreateException() {
        super("I000", "이미지 저장 실패 했습니다.");

    }
}
