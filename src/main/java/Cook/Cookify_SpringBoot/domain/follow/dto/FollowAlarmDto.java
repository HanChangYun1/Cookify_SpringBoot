package Cook.Cookify_SpringBoot.domain.follow.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowAlarmDto {
    private String followerName;
    private String followingName;

    @Builder
    public FollowAlarmDto(String followerName, String followingName) {
        this.followerName = followerName;
        this.followingName = followingName;
    }
}
