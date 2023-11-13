package Cook.Cookify_SpringBoot.repository;

import Cook.Cookify_SpringBoot.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findById(Long id);
}
