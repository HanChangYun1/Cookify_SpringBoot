package Cook.Cookify_SpringBoot.domain.heart;

import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import Cook.Cookify_SpringBoot.global.Entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;



@Getter
@Setter
@NoArgsConstructor
@Entity
public class Heart extends BaseEntity {

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

    //생성 메서드

    public static Heart createHeart(GoogleMember member, Recipe recipe){
        Heart heart = new Heart();
        heart.setMember(member);
        heart.setRecipe(recipe);
        return heart;
    }
}
