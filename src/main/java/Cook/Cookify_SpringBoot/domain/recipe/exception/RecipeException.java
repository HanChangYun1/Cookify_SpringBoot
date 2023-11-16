package Cook.Cookify_SpringBoot.domain.recipe.exception;

import Cook.Cookify_SpringBoot.global.exception.BaseException;
import Cook.Cookify_SpringBoot.global.exception.BaseExceptionType;

public class RecipeException extends BaseException {

    private BaseExceptionType baseExceptionType;

    public RecipeException(BaseExceptionType baseExceptionType) {
        this.baseExceptionType = baseExceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType(){return baseExceptionType;}
}
