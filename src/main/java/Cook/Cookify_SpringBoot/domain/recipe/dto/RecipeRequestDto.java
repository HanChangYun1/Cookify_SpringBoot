package Cook.Cookify_SpringBoot.domain.recipe.dto;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class RecipeRequestDto {
    private String title;
    private String content;
}
