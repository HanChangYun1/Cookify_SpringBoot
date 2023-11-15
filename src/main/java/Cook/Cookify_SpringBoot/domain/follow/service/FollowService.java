package Cook.Cookify_SpringBoot.domain.follow.service;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;

public interface FollowService {

    void addFollow(Long memberId);

    FollowResponseDto getFollow();
}
