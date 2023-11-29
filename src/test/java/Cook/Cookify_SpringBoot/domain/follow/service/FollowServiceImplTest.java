package Cook.Cookify_SpringBoot.domain.follow.service;

import Cook.Cookify_SpringBoot.domain.follow.entity.Follow;
import Cook.Cookify_SpringBoot.domain.follow.repository.FollowRepository;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.entity.Role;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class FollowServiceImplTest {

    @Autowired
    private GoogleMemberRepository googleMemberRepository;
    @Autowired
    private FollowRepository followRepository;
    
    @Test
    public void 팔로우테스트() throws Exception {
        //given
        GoogleMember member1 = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember1 = googleMemberRepository.save(member1);
        GoogleMember member2 = GoogleMember.builder().name("test1").email("test1@gmail.com").picture("https://test1.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember2 = googleMemberRepository.save(member2);
        //when
        Follow follow = followRepository.save(Follow.createFollow(saveMember1, saveMember2));
        //then
        Assertions.assertEquals(saveMember1, followRepository.findById(follow.getId()).orElse(null).getFollower());
        Assertions.assertEquals(saveMember2, followRepository.findById(follow.getId()).orElse(null).getFollowing());
    }

    @Test
    public void 팔로우기능테스트() throws Exception {
        //given
        GoogleMember member1 = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember1 = googleMemberRepository.save(member1);
        GoogleMember member2 = GoogleMember.builder().name("test1").email("test1@gmail.com").picture("https://test1.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember2 = googleMemberRepository.save(member2);
        GoogleMember member3 = GoogleMember.builder().name("test2").email("test2@gmail.com").picture("https://test2.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember3 = googleMemberRepository.save(member3);
        GoogleMember member4 = GoogleMember.builder().name("test3").email("test3@gmail.com").picture("https://test3.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember4 = googleMemberRepository.save(member4);
        //when
        Follow follow1 = followRepository.save(Follow.createFollow(saveMember1, saveMember2));
        Follow follow2 = followRepository.save(Follow.createFollow(saveMember1, saveMember3));
        Follow follow3 = followRepository.save(Follow.createFollow(saveMember1, saveMember4));
        Follow follow4 = followRepository.save(Follow.createFollow(saveMember2, saveMember3));
        Follow follow5 = followRepository.save(Follow.createFollow(saveMember2, saveMember4));
        Follow follow6 = followRepository.save(Follow.createFollow(saveMember3, saveMember4));

        //then
        Assertions.assertEquals(3, followRepository.countByFollower(member1));
        Assertions.assertEquals(2, followRepository.countByFollowing(member3));
        Assertions.assertEquals(3, followRepository.findAllByFollower(member1).size());
        Assertions.assertEquals(3, followRepository.findAllByFollowing(member4).size());
    }
}