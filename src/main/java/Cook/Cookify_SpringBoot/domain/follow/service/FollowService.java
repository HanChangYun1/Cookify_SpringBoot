package Cook.Cookify_SpringBoot.domain.follow.service;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;

public interface FollowService {

    void handlingFollow(Long memberId);

    FollowResponseDto getMyFollow();
}
