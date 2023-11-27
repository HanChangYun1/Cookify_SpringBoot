package Cook.Cookify_SpringBoot.domain.comment;

import Cook.Cookify_SpringBoot.domain.comment.dto.CommentRequestDto;
import Cook.Cookify_SpringBoot.domain.comment.dto.CommentResponseDto;
import Cook.Cookify_SpringBoot.domain.comment.service.CommentService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final CommentService commentService;

    @Autowired
    public CommentResolver(CommentService commentService) {
        this.commentService = commentService;
    }

    public CommentResponseDto saveComment(Long recipeId, CommentRequestDto commentRequestDto) {
        Comment comment = commentService.save(recipeId, commentRequestDto);
        return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getMember().getName());
    }

    public CommentResponseDto saveReComment(Long recipeId, Long parentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentService.saveReComment(recipeId, parentId, commentRequestDto);
        return new CommentResponseDto(comment.getId(), comment.getContent(), comment.getMember().getName());
    }

    public List<CommentResponseDto> getComments(Long recipeId) {
        List<CommentResponseDto> commentDtos = commentService.getComments(recipeId);
        return commentDtos;
    }

    public void updateComment(Long commentId, CommentRequestDto commentRequestDto) {
        commentService.update(commentId, commentRequestDto);
    }

    public void removeComment(Long commentId) {
        commentService.remove(commentId);
    }
}
