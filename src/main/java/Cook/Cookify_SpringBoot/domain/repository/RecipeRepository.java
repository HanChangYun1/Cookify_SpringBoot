package Cook.Cookify_SpringBoot.domain.repository;

import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findById(Long id);
}
