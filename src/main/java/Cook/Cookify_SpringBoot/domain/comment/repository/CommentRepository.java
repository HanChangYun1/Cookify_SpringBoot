package Cook.Cookify_SpringBoot.domain.comment.repository;

import Cook.Cookify_SpringBoot.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByRecipeId(Long RecipeId);
    Optional<Comment> findByParentId(Long parentId);
}
