package Cook.Cookify_SpringBoot.domain.comment.service;


import Cook.Cookify_SpringBoot.domain.comment.Comment;
import Cook.Cookify_SpringBoot.domain.comment.dto.CommentRequestDto;
import Cook.Cookify_SpringBoot.domain.comment.exception.CommentException;


public interface CommentService {
    Comment save(Long postId, CommentRequestDto commentRequestDto);

    Comment saveReComment(Long postId, Long parentId, CommentRequestDto commentRequestDto);

    void update(Long id, CommentRequestDto commentRequestDto);

    void remove(Long id) throws CommentException;
}
