package Cook.Cookify_SpringBoot.domain.recipe;

import Cook.Cookify_SpringBoot.domain.comment.Comment;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.global.Entity.BaseEntity;
import Cook.Cookify_SpringBoot.global.Entity.BaseTimeEntity;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String Content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private GoogleMember member;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ColumnDefault("0")
    private Long recipeCount;

    @ColumnDefault("0")
    private Long HeartCount;


    //생성 메서드//
    public static Recipe createRecipe(GoogleMember member, String title, String content){
        Recipe recipe = new Recipe();
        recipe.confirmMember(member);
        recipe.setTitle(title);
        recipe.setContent(content);

        return recipe;
    }

    //== 연관관계 편의 메서드 ==//
    public void confirmMember(GoogleMember member) {
        this.member = member;
        member.addRecipe(this);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }



    public void update(RecipeRequestDto recipeRequestDto){
        this.title = recipeRequestDto.getTitle();
        this.Content = recipeRequestDto.getContent();
    }

}
