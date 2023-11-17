package Cook.Cookify_SpringBoot.domain.member.exception;

import Cook.Cookify_SpringBoot.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {

    NOT_FOUND_Member(800, HttpStatus.NOT_FOUND, "찾으시는 멤버가 없습니다"),
    NOT_AUTHORITY_UPDATE_Member(801, HttpStatus.FORBIDDEN, "멤버를 업데이트할 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_Member(802, HttpStatus.FORBIDDEN, "멤버를 삭제할 권한이 없습니다.");


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }
}
