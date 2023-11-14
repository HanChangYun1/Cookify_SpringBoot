package Cook.Cookify_SpringBoot.domain.comment.controller;

import Cook.Cookify_SpringBoot.domain.comment.dto.CommentRequestDto;
import Cook.Cookify_SpringBoot.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/{recipeId}")
    public void commentSave(@PathVariable("recipeId") Long postId, CommentRequestDto commentRequestDto){
        commentService.save(postId, commentRequestDto);
    }


    @PostMapping("/{recipeId}/{commentId}")
    public void reCommentSave(@PathVariable("recipeId") Long postId,
                              @PathVariable("commentId") Long commentId,
                              CommentRequestDto commentRequestDto){
        commentService.saveReComment(postId, commentId, commentRequestDto);
    }


    @PutMapping("/{commentId}")
    public void update(@PathVariable("commentId") Long commentId,
                       CommentRequestDto commentRequestDto){
        commentService.update(commentId, commentRequestDto);
    }


    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId){
        commentService.remove(commentId);
    }
}
