package Cook.Cookify_SpringBoot.domain.recipe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class RecipeRequestDto {
    @NotBlank(message = "Title cannot be blank")
    private String title;
    @NotNull(message = "Ingredients cannot be null")
    private List<String> ingredients1;
    private List<String> ingredients2;
    @NotNull(message = "Ingredients cannot be null")
    private List<String> steps;
    @NotNull(message = "Ingredients cannot be null")
    private String thumbnail;

    @Builder
    public RecipeRequestDto( String title,
                             List<String> ingredients1,
                             List<String> ingredients2,
                             List<String> steps,
                             String thumbnail) {
        this.title = title;
        this.ingredients1 = ingredients1;
        this.ingredients2 = ingredients2;
        this.steps = steps;
        this.thumbnail = thumbnail;
    }
}
