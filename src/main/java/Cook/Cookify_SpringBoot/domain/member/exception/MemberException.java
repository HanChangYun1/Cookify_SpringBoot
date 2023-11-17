package Cook.Cookify_SpringBoot.domain.member.exception;

import Cook.Cookify_SpringBoot.global.exception.BaseException;
import Cook.Cookify_SpringBoot.global.exception.BaseExceptionType;

public class MemberException extends BaseException {

    private BaseExceptionType baseExceptionType;

    public MemberException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }
    @Override
    public BaseExceptionType getExceptionType(){return this.baseExceptionType;}
}
