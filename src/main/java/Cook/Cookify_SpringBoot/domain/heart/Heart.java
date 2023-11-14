package Cook.Cookify_SpringBoot.domain.heart;

import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Getter
@NoArgsConstructor
@Entity
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "member_id")
    private GoogleMember member;

    @ManyToOne()
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
