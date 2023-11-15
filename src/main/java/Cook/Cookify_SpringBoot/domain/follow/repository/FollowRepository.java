package Cook.Cookify_SpringBoot.domain.follow.repository;

import Cook.Cookify_SpringBoot.domain.follow.Follow;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(GoogleMember follower, GoogleMember following);
    void deleteByFollowerAndFollowing(GoogleMember follower, GoogleMember following);


    Long countByFollower(GoogleMember member);   //팔로워 수
    Long countByFollowing(GoogleMember member);  // 팔로잉 수

    List<Follow> findAllByFollower(GoogleMember member);
    List<Follow> findAllByFollowing(GoogleMember member);
}
