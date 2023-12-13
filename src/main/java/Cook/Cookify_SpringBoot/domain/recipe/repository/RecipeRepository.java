package Cook.Cookify_SpringBoot.domain.recipe.repository;

import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select r from Recipe r join fetch r.member m")
    List<Recipe> findAllWithMemberComment(PageRequest of);
    @Query("select r from Recipe r join fetch r.member m where r.member.id = :memberId")
    List<Recipe> findAllByMember(@Param("memberId") Long memberId);

    List<Recipe> findAllByTitleContaining(String keyword, PageRequest of);

    @Query(value = "SELECT recipe_id, null AS recipeDocsId, title, thumbnail FROM Recipe WHERE title LIKE %:keyword% " +
            "UNION ALL " +
            "SELECT null AS recipeId, id AS recipeDocsId, title, thumbnail FROM recipe_docs WHERE title LIKE %:keyword%", nativeQuery = true)
    List<Object[]> findAllTitlesContaining(@Param("keyword") String keyword, PageRequest of);
}
