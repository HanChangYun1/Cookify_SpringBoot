package Cook.Cookify_SpringBoot.domain.comment.repository;

import Cook.Cookify_SpringBoot.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdAndParentCommentIsNull(Long postId);
    List<Comment> findByParentId(Long parentId);
}
