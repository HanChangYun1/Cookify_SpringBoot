package Cook.Cookify_SpringBoot.domain.heart.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HeartCountDto {
    private int heartCount;
    @Builder
    public HeartCountDto(int heartCount) {
        this.heartCount = heartCount;
    }
}
