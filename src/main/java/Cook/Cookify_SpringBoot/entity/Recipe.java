package Cook.Cookify_SpringBoot.entity;

import Cook.Cookify_SpringBoot.controller.RecipeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Recipe extends  BaseTimeEntity{

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
    private GoogleMember googleMember;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void update(RecipeRequestDto recipeRequestDto){
        this.title = recipeRequestDto.getTitle();
        this.Content = recipeRequestDto.getContent();
    }
}
