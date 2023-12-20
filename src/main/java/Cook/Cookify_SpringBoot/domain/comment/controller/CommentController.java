package Cook.Cookify_SpringBoot.domain.comment.controller;

import Cook.Cookify_SpringBoot.domain.comment.dto.CommentRequestDto;
import Cook.Cookify_SpringBoot.domain.comment.dto.CommentResponseDto;
import Cook.Cookify_SpringBoot.domain.comment.entity.Comment;
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
    public ResponseEntity<CommentResponseDto> commentSave(@PathVariable("recipeId") Long recipeId,@RequestBody CommentRequestDto commentRequestDto){
        Comment comment = commentService.save(recipeId, commentRequestDto);
        CommentResponseDto dto = new CommentResponseDto(comment.getId(), comment.getContent(), comment.getMember(), comment.isRemoved());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @PostMapping("/{recipeId}/{commentId}")
    public ResponseEntity<CommentResponseDto> reCommentSave(@PathVariable("recipeId") Long recipeId,
                              @PathVariable("commentId") Long commentId,
                              @RequestBody CommentRequestDto commentRequestDto){
        Comment comment = commentService.saveReComment(recipeId, commentId, commentRequestDto);
        CommentResponseDto dto = new CommentResponseDto(comment.getId(), comment.getContent(), comment.getMember(), comment.isRemoved());
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
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
