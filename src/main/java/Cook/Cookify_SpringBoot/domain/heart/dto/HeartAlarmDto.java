package Cook.Cookify_SpringBoot.domain.heart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HeartAlarmDto {
    private String memberName;
    private String recipeMemberName;
    private String recipeName;
    private Long recipeId;

    @Builder
    public HeartAlarmDto(String memberName, String recipeMemberName, String recipeName, Long recipeId) {
        this.memberName = memberName;
        this.recipeMemberName = recipeMemberName;
        this.recipeName = recipeName;
        this.recipeId = recipeId;
    }
}
