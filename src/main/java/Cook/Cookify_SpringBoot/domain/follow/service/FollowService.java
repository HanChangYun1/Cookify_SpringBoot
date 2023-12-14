package Cook.Cookify_SpringBoot.domain.follow.service;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowAlarmDto;
import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;
import Cook.Cookify_SpringBoot.domain.follow.entity.Follow;

import java.util.Optional;

public interface FollowService {

    FollowAlarmDto handleFollow(Long memberId);

    FollowResponseDto getMyFollow();
}
