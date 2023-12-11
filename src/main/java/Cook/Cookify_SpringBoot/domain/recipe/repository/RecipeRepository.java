package Cook.Cookify_SpringBoot.domain.recipe.repository;

import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("select r from Recipe r join fetch r.member m")
    List<Recipe> findAllWithMemberComment(PageRequest of);
    @Query("select r from Recipe r join fetch r.member m where r.member.id = :memberId")
    List<Recipe> findAllByMember(@Param("memberId") Long memberId);
}
