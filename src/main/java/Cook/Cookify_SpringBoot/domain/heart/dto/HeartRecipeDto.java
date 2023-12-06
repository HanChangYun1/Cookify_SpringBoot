package Cook.Cookify_SpringBoot.domain.heart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HeartRecipeDto {
    private Long id;
    private String title;
    private String thumbnail;
    private int heartCount;

    @Builder
    public HeartRecipeDto(Long id, String title, String thumbnail, int heartCount) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.heartCount = heartCount;
    }
}
