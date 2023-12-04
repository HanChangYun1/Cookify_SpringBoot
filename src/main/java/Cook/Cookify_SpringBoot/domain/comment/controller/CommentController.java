package Cook.Cookify_SpringBoot.domain.comment.controller;

import Cook.Cookify_SpringBoot.domain.comment.dto.CommentRequestDto;
import Cook.Cookify_SpringBoot.domain.comment.dto.CommentResponseDto;
import Cook.Cookify_SpringBoot.domain.comment.exception.CommentException;
import Cook.Cookify_SpringBoot.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {


    private final CommentService commentService;

    @PostMapping("/{recipeId}")
    public void commentSave(@PathVariable("recipeId") Long recipeId,@RequestBody CommentRequestDto commentRequestDto){
        commentService.save(recipeId, commentRequestDto);
    }


    @PostMapping("/{recipeId}/{commentId}")
    public void reCommentSave(@PathVariable("recipeId") Long recipeId,
                              @PathVariable("commentId") Long commentId,
                              @RequestBody CommentRequestDto commentRequestDto){
        commentService.saveReComment(recipeId, commentId, commentRequestDto);
    }

    @GetMapping("/{recipeId}")
    public List<CommentResponseDto> getComments(@PathVariable("recipeId") Long recipeId){
        return commentService.getComments(recipeId);
    }


    @PutMapping("/{commentId}")
    public void update(@PathVariable("commentId") Long commentId,
                       @RequestBody CommentRequestDto commentRequestDto){
        commentService.update(commentId, commentRequestDto);
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> delete(@PathVariable("commentId") Long commentId){
        try {
            commentService.remove(commentId);
            return new ResponseEntity<>("댓글 삭제 성공", HttpStatus.OK);
        }catch (CommentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
