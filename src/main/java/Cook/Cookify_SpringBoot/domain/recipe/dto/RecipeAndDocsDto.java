package Cook.Cookify_SpringBoot.domain.recipe.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecipeAndDocsDto {
    private Long recipeId;
    private Long recipeDocsId;
    private String recipeTitle;
    private String recipeThumbnail;
}
