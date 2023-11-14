package Cook.Cookify_SpringBoot.domain.comment.service;

import Cook.Cookify_SpringBoot.domain.comment.Comment;
import Cook.Cookify_SpringBoot.domain.comment.dto.CommentRequestDto;
import Cook.Cookify_SpringBoot.domain.comment.exception.CommentException;
import Cook.Cookify_SpringBoot.domain.comment.exception.CommentExceptionType;
import Cook.Cookify_SpringBoot.domain.comment.repository.CommentRepository;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final GoogleMemberRepository memberRepository;
    private final RecipeRepository recipeRepository;

    @Override
    public Comment save(Long recipeId, CommentRequestDto commentRequestDto) {

        String loginUserEmail = SecurityUtil.getLoginUserEmail();
        GoogleMember member = memberRepository.findByEmail(loginUserEmail).orElse(null);

        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);

        Comment comment = Comment.createComment(member, recipe, commentRequestDto.getContent());

         return commentRepository.save(comment);

    }

    @Override
    public Comment saveReComment(Long recipeId, Long parentId, CommentRequestDto commentRequestDto) {
        String loginUserEmail = SecurityUtil.getLoginUserEmail();
        GoogleMember member = memberRepository.findByEmail(loginUserEmail).orElse(null);

        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        Comment parent = commentRepository.findByParentId(parentId).orElse(null);


        Comment comment = Comment.createComment(member, recipe, commentRequestDto.getContent());
        comment.confirmParent(parent);

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments(Long recipeId){
        List<Comment> comments = commentRepository.findByRecipeId(recipeId);
        return comments;
    }



    @Override
    public void update(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));
        comment.updateContent(commentRequestDto.getContent());
    }



    @Override
    public void remove(Long commentId) throws CommentException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        commentRepository.deleteAll(removableCommentList);
    }
}
