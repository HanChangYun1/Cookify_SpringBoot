package Cook.Cookify_SpringBoot.domain.comment.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
