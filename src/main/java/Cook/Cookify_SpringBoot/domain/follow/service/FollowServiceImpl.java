package Cook.Cookify_SpringBoot.domain.follow.service;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowAlarmDto;
import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;
import Cook.Cookify_SpringBoot.domain.follow.entity.Follow;
import Cook.Cookify_SpringBoot.domain.follow.repository.FollowRepository;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
    private final GoogleMemberRepository googleMemberRepository;
    private final HttpSession httpSession;

    @Transactional
    public FollowAlarmDto handleFollow(Long memberId){
        String email = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember follower = googleMemberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));
        GoogleMember following = googleMemberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));

        if (!followRepository.existsByFollowerAndFollowing(follower,following)){
            Follow follow = followRepository.save(Follow.createFollow(follower, following));
            return FollowAlarmDto.builder()
                    .followerName(follow.getFollower().getName())
                    .followingName(follow.getFollowing().getName()).build();
        }else {
            followRepository.deleteByFollowerAndFollowing(follower, following);
            return FollowAlarmDto.builder().build();
        }
    }


    public FollowResponseDto getMyFollow(){
        String email = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember member = googleMemberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));

        Long followingCount = followRepository.countByFollowing(member);

        List<Follow> followers = followRepository.findAllByFollower(member);

        List<Follow> followings = followRepository.findAllByFollowing(member);

        List<Follow> follow4follow = new ArrayList<>();
        for (Follow following : followings){
            for (Follow follower : followers){
                if(following.getFollower().equals(follower.getFollowing()) ){
                    follow4follow.add(following);
                    break;
                }
            }
        }

        List<Follow> deduplicatingFollowing = followings.stream().filter(following -> follow4follow.stream().noneMatch(f4f -> f4f.equals(following))).collect(Collectors.toList());
        return FollowResponseDto.builder()
                .followingCount(followingCount)
                .follow4follow(follow4follow)
                .followingList(deduplicatingFollowing).build();
    }
}
