package Cook.Cookify_SpringBoot.domain.heart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HeartAlarmDto {
    private String member;
    private String recipe;

    @Builder
    public HeartAlarmDto(String member, String recipe) {
        this.member = member;
        this.recipe = recipe;
    }
}
