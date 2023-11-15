package Cook.Cookify_SpringBoot.domain.follow;

import Cook.Cookify_SpringBoot.domain.BaseTimeEntity;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Follow extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private GoogleMember follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private GoogleMember following;


    //생성 메서드//
    public static Follow createFollow(GoogleMember follower, GoogleMember following){
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        return follow;
    }
}
