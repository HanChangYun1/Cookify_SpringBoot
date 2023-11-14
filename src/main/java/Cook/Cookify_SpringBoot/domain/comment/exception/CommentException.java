package Cook.Cookify_SpringBoot.domain.comment.exception;

import Cook.Cookify_SpringBoot.global.exception.BaseException;
import Cook.Cookify_SpringBoot.global.exception.BaseExceptionType;

public class CommentException extends BaseException {

    private BaseExceptionType baseExceptionType;


    public CommentException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return this.baseExceptionType;
    }
}
