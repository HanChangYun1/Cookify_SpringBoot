package Cook.Cookify_SpringBoot.domain.follow;

import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Follow {

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
}
