package Cook.Cookify_SpringBoot.domain.recipe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeRequestDto {
    private String title;
    private List<String> ingredients;
    private List<String> ingredients2;
    private List<String> steps;
    private String thumbnail;

    @JsonCreator
    public RecipeRequestDto(@JsonProperty("title") String title,
                            @JsonProperty("ingredients") List<String> ingredients,
                            @JsonProperty("ingredients2") List<String> ingredients2,
                            @JsonProperty("steps") List<String> steps,
                            @JsonProperty("thumbnail") String thumbnail) {
        this.title = title;
        this.ingredients = ingredients;
        this.ingredients2 = ingredients2;
        this.steps = steps;
        this.thumbnail = thumbnail;
    }
}
