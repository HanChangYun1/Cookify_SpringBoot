package Cook.Cookify_SpringBoot.domain.follow.dto;

import Cook.Cookify_SpringBoot.domain.follow.entity.Follow;
import lombok.Data;

import java.util.List;

@Data
public class FollowResponseDto {
    private Long followerCount;
    private Long followingCount;
    private List<Follow> followerList;
    private List<Follow> followingList;

    public FollowResponseDto(Long followerCount, Long followingCount, List<Follow> followerList, List<Follow> followingList) {
        this.followerCount = followerCount;
        this.followingCount = followingCount;
        this.followerList = followerList;
        this.followingList = followingList;
    }
}
