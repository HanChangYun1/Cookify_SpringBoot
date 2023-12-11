package Cook.Cookify_SpringBoot.domain.follow.service;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;
import Cook.Cookify_SpringBoot.domain.follow.entity.Follow;

public interface FollowService {

    Follow addFollow(Long memberId);

    void deleteFollow(Long memberId);

    FollowResponseDto getMyFollow();
}
