package Cook.Cookify_SpringBoot.domain.follow.repository;

import Cook.Cookify_SpringBoot.domain.follow.entity.Follow;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerAndFollowing(GoogleMember follower, GoogleMember following);
    void deleteByFollowerAndFollowing(GoogleMember follower, GoogleMember following);
    

    Long countByFollowing(GoogleMember member);  // 팔로잉 수

    @Query("select f from Follow f left join fetch f.following where f.follower = :follower") //내가 팔로우한 사람들
    List<Follow> findAllByFollower(@Param("follower") GoogleMember member);

    @Query("select f from Follow f left join fetch f.follower where f.following = :following") //나를 팔로우한 사람들
    List<Follow> findAllByFollowing(@Param("following") GoogleMember member);
}
