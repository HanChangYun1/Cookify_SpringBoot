package Cook.Cookify_SpringBoot.domain.follow.dto;

import Cook.Cookify_SpringBoot.domain.follow.entity.Follow;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FollowResponseDto {

    private Long followingCount;
    private List<Follow> follow4follow;
    private List<Follow> followingList;

    @Builder
    public FollowResponseDto(Long followingCount, List<Follow> follow4follow, List<Follow> followingList) {
        this.followingCount = followingCount;
        this.follow4follow = follow4follow;
        this.followingList = followingList;
    }
}
