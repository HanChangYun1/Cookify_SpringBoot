package Cook.Cookify_SpringBoot.domain.recipe.dto;

import lombok.Data;

import javax.persistence.Column;
@Data
public class BriefRecipeDto {

    private Long id;
    private String title;
    private String thumbnail;

    public BriefRecipeDto(Long id, String title, String thumbnail) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
    }
}
