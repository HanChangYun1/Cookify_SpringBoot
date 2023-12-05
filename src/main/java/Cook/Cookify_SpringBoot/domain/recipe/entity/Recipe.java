package Cook.Cookify_SpringBoot.domain.recipe.entity;

import Cook.Cookify_SpringBoot.domain.comment.entity.Comment;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.global.Entity.BaseEntity;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    @Column(nullable = false)
    private String title;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> ingredients2;
    @ElementCollection
    private List<String> steps;
    private String  thumbnail;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_docs_id")
    private RecipeDocs recipeDocs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private GoogleMember member;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();


    private int recipeCount;

    private int HeartCount;


    //생성 메서드//
    public static Recipe createRecipe(GoogleMember member, RecipeRequestDto recipeRequestDto, String imgUrl){
        Recipe recipe = new Recipe();
        recipe.confirmMember(member);
        recipe.setTitle(recipeRequestDto.getTitle());
        recipe.setIngredients(recipeRequestDto.getIngredients());
        recipe.setIngredients2(recipeRequestDto.getIngredients2());
        recipe.setSteps(recipeRequestDto.getSteps());
        recipe.setThumbnail(imgUrl);

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



    public void update(RecipeRequestDto recipeRequestDto, String imgUrl){
        this.title = recipeRequestDto.getTitle();
        this.ingredients = recipeRequestDto.getIngredients();
        this.ingredients2 = recipeRequestDto.getIngredients2();
        this.steps = recipeRequestDto.getSteps();
        this.thumbnail = imgUrl;
    }

}
