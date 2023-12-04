package Cook.Cookify_SpringBoot.domain.recipe.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class RecipeRequestDto {
    private String title;
    private List<String> ingredients;
    private List<String> ingredients2;
    private List<String> steps;
    private String thumbnail;

    public RecipeRequestDto(String title, List<String> ingredients, List<String> ingredients2, List<String> steps, String thumbnail) {
        this.title = title;
        this.ingredients = ingredients;
        this.ingredients2 = ingredients2;
        this.steps = steps;
        this.thumbnail = thumbnail;
    }
}
