package Cook.Cookify_SpringBoot.domain.recipe.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class RecipeRequestDto {
    private String title;
    private String ingredients1;
    private String ingredients2;
    private String steps;
    private String thumbnail;

    public RecipeRequestDto(String title, String ingredients1, String ingredients2, String steps, String thumbnail) {
        this.title = title;
        this.ingredients1 = ingredients1;
        this.ingredients2 = ingredients2;
        this.steps = steps;
        this.thumbnail = thumbnail;
    }
}
