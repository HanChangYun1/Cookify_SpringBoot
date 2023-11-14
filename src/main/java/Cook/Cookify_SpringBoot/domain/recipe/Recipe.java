package Cook.Cookify_SpringBoot.domain.recipe;

import Cook.Cookify_SpringBoot.domain.comment.Comment;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.BaseTimeEntity;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe extends BaseTimeEntity {

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


    //== 연관관계 편의 메서드 ==//
    public void confirmWriter(GoogleMember member) {
        this.member = member;
        member.addPost(this);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }



    public void update(RecipeRequestDto recipeRequestDto){
        this.title = recipeRequestDto.getTitle();
        this.Content = recipeRequestDto.getContent();
    }

}
