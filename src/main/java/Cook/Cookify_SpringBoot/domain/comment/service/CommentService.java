package Cook.Cookify_SpringBoot.domain.comment.service;


import Cook.Cookify_SpringBoot.domain.comment.dto.CommentSaveDto;
import Cook.Cookify_SpringBoot.domain.comment.dto.CommentUpdateDto;
import Cook.Cookify_SpringBoot.domain.comment.exception.CommentException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


public interface CommentService {
    void save(Long postId, CommentSaveDto commentSaveDto);

    void saveReComment(Long postId, Long parentId, CommentSaveDto commentSaveDto);

    void update(Long id, CommentUpdateDto commentUpdateDto);

    void remove(Long id) throws CommentException;
}
