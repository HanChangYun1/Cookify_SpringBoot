package Cook.Cookify_SpringBoot.domain.recipe.dto;

import lombok.Data;

import javax.persistence.Column;
@Data
public class BriefRecipeDto {

    private String title;
    private String thumbnail;

    public BriefRecipeDto(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }
}
