package Cook.Cookify_SpringBoot.domain.recipe.repository;

import Cook.Cookify_SpringBoot.domain.recipe.RecipeDocs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeDocsRepository extends JpaRepository<RecipeDocs, Long> {
}
