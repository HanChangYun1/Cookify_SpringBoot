package Cook.Cookify_SpringBoot.domain.heart.dto;

import lombok.Data;

@Data
public class HeartRecipeDto {
    private String title;
    private String thumbnail;

    public HeartRecipeDto(String title, String thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }
}
