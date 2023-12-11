package Cook.Cookify_SpringBoot.domain.follow.service;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowAlarmDto;
import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;

public interface FollowService {

    FollowAlarmDto addFollow(Long memberId);

    void deleteFollow(Long memberId);

    FollowResponseDto getMyFollow();
}
