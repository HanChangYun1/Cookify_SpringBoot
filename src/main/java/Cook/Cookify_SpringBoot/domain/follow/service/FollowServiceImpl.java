package Cook.Cookify_SpringBoot.domain.follow.service;

import Cook.Cookify_SpringBoot.domain.follow.Follow;
import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;
import Cook.Cookify_SpringBoot.domain.follow.repository.FollowRepository;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final GoogleMemberRepository googleMemberRepository;

    public void addFollow(Long memberId){
        String email = SecurityUtil.getLoginUserEmail();
        GoogleMember follower = googleMemberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));
        GoogleMember following = googleMemberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));

        if (!followRepository.existsByFollowerAndFollowing(follower, following)){
            followRepository.save(Follow.createFollow(follower,following));
        }else {
            followRepository.deleteByFollowerAndFollowing(follower,following);
        }
    }

    public FollowResponseDto getFollow(){
        String email = SecurityUtil.getLoginUserEmail();
        GoogleMember member = googleMemberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));
        Long followerCount = followRepository.countByFollower(member);
        Long followingCount = followRepository.countByFollowing(member);
        List<Follow> followers = followRepository.findAllByFollower(member);   //사용자가 팔로잉 한 사람들
        List<Follow> followings = followRepository.findAllByFollowing(member);   //사용자를 팔로우한 사람들
        return new FollowResponseDto(followerCount,followingCount,followers,followings);
    }
}
