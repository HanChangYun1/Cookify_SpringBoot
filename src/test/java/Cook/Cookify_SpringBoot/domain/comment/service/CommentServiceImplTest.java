package Cook.Cookify_SpringBoot.domain.comment.service;

import Cook.Cookify_SpringBoot.domain.comment.entity.Comment;
import Cook.Cookify_SpringBoot.domain.comment.dto.CommentResponseDto;
import Cook.Cookify_SpringBoot.domain.comment.exception.CommentException;
import Cook.Cookify_SpringBoot.domain.comment.exception.CommentExceptionType;
import Cook.Cookify_SpringBoot.domain.comment.repository.CommentRepository;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.entity.Role;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class CommentServiceImplTest {

    @Autowired
    private GoogleMemberRepository googleMemberRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentService commentService;

    @Test
    public void 댓글작성() throws Exception {
        //given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(member, new RecipeRequestDto("title", "ing1", "ing2", "step", "thumb"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        //when
        Comment comment = Comment.createComment(saveMember, saveRecipe, "test 댓글");
        Comment saveComment = commentRepository.save(comment);

        //then
        Assertions.assertEquals(saveMember.getId(), saveComment.getMember().getId());
        Assertions.assertEquals(saveRecipe.getId(), saveComment.getRecipe().getId());
        Assertions.assertEquals("test 댓글", saveComment.getContent());
    }

    @Test
    public void 대댓글작성() throws Exception {
        //given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(member, new RecipeRequestDto("title", "ing1", "ing2", "step", "thumb"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        Comment parentComment = Comment.createComment(saveMember, saveRecipe, "부모 댓글");
        Comment saveParentComment = commentRepository.save(parentComment);

        //when
        Comment childComment = Comment.createComment(saveMember, saveRecipe, "자식 댓글");
        childComment.confirmParent(parentComment);
        Comment saveChildComment = commentRepository.save(childComment);

        //then
        Assertions.assertEquals(saveMember.getId(), saveChildComment.getMember().getId());
        Assertions.assertEquals(saveRecipe.getId(), saveChildComment.getRecipe().getId());
        Assertions.assertEquals("자식 댓글", saveChildComment.getContent());
        Assertions.assertEquals(saveParentComment.getId(), saveChildComment.getParent().getId());
    }

    @Test
    public void 댓글수정() throws Exception {
        //given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(saveMember, new RecipeRequestDto("title", "ing1", "ing2", "step", "thumb"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        Comment comment = Comment.createComment(saveMember, saveRecipe, "원래 댓글");
        Comment saveComment = commentRepository.save(comment);

        //when
        comment.updateContent("수정된 댓글");

        //then

        assertEquals("수정된 댓글", saveComment.getContent());
    }

    @Test
    public void 댓글삭제() throws Exception {
        //given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(saveMember, new RecipeRequestDto("title", "ing1", "ing2", "step", "thumb"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        Comment comment = Comment.createComment(saveMember, saveRecipe, "삭제할 댓글");
        Comment reComment1 = Comment.createComment(saveMember, saveRecipe, "삭제할 대댓글1");
        reComment1.confirmParent(comment);
        Comment reComment2 = Comment.createComment(saveMember, saveRecipe, "삭제할 대댓글2");
        reComment2.confirmParent(comment);
        Comment saveComment1 = commentRepository.save(comment);
        Comment saveComment2 = commentRepository.save(reComment1);
        Comment saveComment3 = commentRepository.save(reComment2);

        //when
        comment.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        commentRepository.deleteAll(removableCommentList);

        //then
        Assertions.assertEquals(true, comment.isRemoved());
    }

    @Test
    public void 댓글전부삭제() throws Exception {
        //given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(saveMember, new RecipeRequestDto("title", "ing1", "ing2", "step", "thumb"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        Comment comment = Comment.createComment(saveMember, saveRecipe, "삭제할 댓글");
        Comment reComment1 = Comment.createComment(saveMember, saveRecipe, "삭제할 대댓글1");
        reComment1.confirmParent(comment);
        Comment reComment2 = Comment.createComment(saveMember, saveRecipe, "삭제할 대댓글2");
        reComment2.confirmParent(comment);
        Comment saveComment1 = commentRepository.save(comment);
        Comment saveComment2 = commentRepository.save(reComment1);
        Comment saveComment3 = commentRepository.save(reComment2);

        //when
        comment.remove();
        reComment1.remove();
        reComment2.remove();
        List<Comment> removableCommentList = comment.findRemovableList();
        commentRepository.deleteAll(removableCommentList);

        //then
        Assertions.assertThrows(CommentException.class, () -> commentRepository.findById(saveComment1.getId()).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT)));
        Assertions.assertThrows(CommentException.class, () -> commentRepository.findById(saveComment2.getId()).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT)));
        Assertions.assertThrows(CommentException.class, () -> commentRepository.findById(saveComment3.getId()).orElseThrow(() -> new CommentException(CommentExceptionType.NOT_FOUND_COMMENT)));
    }

    @Test
    public void 댓글조회() throws Exception {
        //given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(saveMember, new RecipeRequestDto("title", "ing1", "ing2", "step", "thumb"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        Comment comment1 = Comment.createComment(saveMember, saveRecipe, "댓글1");
        Comment comment2 = Comment.createComment(saveMember, saveRecipe, "댓글2");
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        //when
        List<CommentResponseDto> comments = commentService.getComments(saveRecipe.getId());

        //then
        assertEquals(2, comments.size());
        assertEquals("댓글1", comments.get(0).getContent());
        assertEquals("댓글2", comments.get(1).getContent());
    }
}