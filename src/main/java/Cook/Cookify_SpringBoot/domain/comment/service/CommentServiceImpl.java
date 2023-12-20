package Cook.Cookify_SpringBoot.domain.comment.service;

import Cook.Cookify_SpringBoot.domain.comment.entity.Comment;
import Cook.Cookify_SpringBoot.domain.comment.dto.CommentRequestDto;
import Cook.Cookify_SpringBoot.domain.comment.dto.CommentResponseDto;
import Cook.Cookify_SpringBoot.domain.comment.exception.CommentException;
import Cook.Cookify_SpringBoot.domain.comment.exception.CommentExceptionType;
import Cook.Cookify_SpringBoot.domain.comment.repository.CommentRepository;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeException;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeExceptionType;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final GoogleMemberRepository memberRepository;
    private final RecipeRepository recipeRepository;

    private final HttpSession httpSession;


    @Transactional
    public Comment save(Long recipeId, CommentRequestDto commentRequestDto) {

        String loginUserEmail = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember member = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));
        Comment comment = Comment.createComment(member, recipe, commentRequestDto.getContent());

         return commentRepository.save(comment);

    }


    @Transactional
    public Comment saveReComment(Long recipeId, Long parentId, CommentRequestDto commentRequestDto) {
        String loginUserEmail = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember member = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));
        Comment parent = commentRepository.findById(parentId).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));

        Comment comment = Comment.createComment(member, recipe, commentRequestDto.getContent());
        comment.confirmParent(parent);

        return commentRepository.save(comment);
    }


    public List<CommentResponseDto> getComments(Long recipeId){
        List<Comment> comments = commentRepository.findByRecipeId(recipeId);
        List<CommentResponseDto> commentDtos = comments.stream().map(c -> new CommentResponseDto(c.getId(), c.getContent(), c.getMember(), c.isRemoved())).collect(Collectors.toList());

        return commentDtos;
    }




    @Transactional
    public void update(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));
        if (!comment.getMember().getId().equals(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail(httpSession)).get().getId())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_UPDATE_COMMENT);
        }

        comment.updateContent(commentRequestDto.getContent());
    }




    @Transactional
    public void remove(Long commentId) throws CommentException {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT));
        if (!comment.getMember().getId().equals(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail(httpSession)).get().getId())){
            throw new CommentException(CommentExceptionType.NOT_AUTHORITY_DELETE_COMMENT);
        }

        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        commentRepository.deleteAll(removableCommentList);
    }
}
