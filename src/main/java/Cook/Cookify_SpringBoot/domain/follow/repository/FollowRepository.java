package Cook.Cookify_SpringBoot.domain.follow.repository;

import Cook.Cookify_SpringBoot.domain.follow.Follow;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(GoogleMember follower, GoogleMember following);
    void deleteByFollowerAndFollowing(GoogleMember follower, GoogleMember following);


    Long countByFollower(GoogleMember member);   //팔로워 수
    Long countByFollowing(GoogleMember member);  // 팔로잉 수

    @Query("select f from Follow f left join fetch f.following m where f.follower = :follower") //내가 팔로우한 사람들
    List<Follow> findAllByFollower(@Param("follower") GoogleMember member);

    @Query("select f from Follow f left join fetch f.follower m where f.following = :following") //나를 팔로우한 사람들
    List<Follow> findAllByFollowing(@Param("following") GoogleMember member);
}
