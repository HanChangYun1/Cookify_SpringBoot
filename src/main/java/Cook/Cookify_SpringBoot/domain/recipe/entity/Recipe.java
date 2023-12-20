package Cook.Cookify_SpringBoot.domain.recipe.entity;

import Cook.Cookify_SpringBoot.domain.comment.entity.Comment;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.global.Entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String title;
    @ElementCollection
    private List<String> ingredients1;
    @ElementCollection
    private List<String> ingredients2;
    @ElementCollection
    private List<String> steps;
    @NotNull
    private String  thumbnail;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private GoogleMember member;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();


    private int recipeCount;

    private int HeartCount;


    //생성 메서드//
    public static Recipe createRecipe(GoogleMember member, RecipeRequestDto recipeRequestDto){
        Recipe recipe = new Recipe();
        recipe.confirmMember(member);
        recipe.setTitle(recipeRequestDto.getTitle());
        recipe.setIngredients1(recipeRequestDto.getIngredients1());
        recipe.setIngredients2(recipeRequestDto.getIngredients2());
        recipe.setSteps(recipeRequestDto.getSteps());
        recipe.setThumbnail(recipeRequestDto.getThumbnail());

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
        this.ingredients1 = recipeRequestDto.getIngredients1();
        this.ingredients2 = recipeRequestDto.getIngredients2();
        this.steps = recipeRequestDto.getSteps();
        this.thumbnail = recipeRequestDto.getThumbnail();
    }

}
