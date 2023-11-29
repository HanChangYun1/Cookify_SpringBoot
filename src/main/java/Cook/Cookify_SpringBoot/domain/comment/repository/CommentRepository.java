package Cook.Cookify_SpringBoot.domain.comment.repository;

import Cook.Cookify_SpringBoot.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c left join fetch c.member m where c.recipe.id = :recipeId ")
    List<Comment> findByRecipeId(@Param("recipeId") Long recipeId);

    Optional<Comment> findByParentId(Long parentId);
}
