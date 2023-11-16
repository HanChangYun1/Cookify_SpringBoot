package Cook.Cookify_SpringBoot.domain.recipe.exception;

import Cook.Cookify_SpringBoot.global.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum RecipeExceptionType implements BaseExceptionType {

    NOT_FOUND_Recipe(800, HttpStatus.NOT_FOUND, "찾으시는 레시피가 없습니다"),
    NOT_AUTHORITY_UPDATE_Recipe(801, HttpStatus.FORBIDDEN, "레시피룰 업데이트할 권한이 없습니다."),
    NOT_AUTHORITY_DELETE_Recipe(802, HttpStatus.FORBIDDEN, "레시피룰 삭제할 권한이 없습니다.");


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    RecipeExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
