package Cook.Cookify_SpringBoot.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Getter
@NoArgsConstructor
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private GoogleMember member;

    @ManyToOne()
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
