package Cook.Cookify_SpringBoot.domain.recipe.repository;

import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.entity.RecipeDocs;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeDocsRepository extends JpaRepository<RecipeDocs, Long> {
    List<RecipeDocs> findAllByTitleContaining(String keyword, PageRequest of);
}
