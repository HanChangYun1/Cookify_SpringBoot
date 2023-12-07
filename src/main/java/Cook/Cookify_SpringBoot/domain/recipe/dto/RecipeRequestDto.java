package Cook.Cookify_SpringBoot.domain.recipe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class RecipeRequestDto {
    private String title;
    private List<String> ingredients;
    private List<String> ingredients2;
    private List<String> steps;
    private String thumbnail;

    @Builder
    public RecipeRequestDto( String title,
                             List<String> ingredients,
                             List<String> ingredients2,
                             List<String> steps,
                             String thumbnail) {
        this.title = title;
        this.ingredients = ingredients;
        this.ingredients2 = ingredients2;
        this.steps = steps;
        this.thumbnail = thumbnail;
    }
}
