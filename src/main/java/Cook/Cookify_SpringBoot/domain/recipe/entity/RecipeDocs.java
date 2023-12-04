package Cook.Cookify_SpringBoot.domain.recipe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "recipe_docs")
public class RecipeDocs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 5000)
    private String title;
    @Column(length = 5000)
    private String ingredients;
    @Column(length = 5000)
    private String ingredients2;
    @Column(length = 5000)
    private String steps;

    private String thumbnail;
}
