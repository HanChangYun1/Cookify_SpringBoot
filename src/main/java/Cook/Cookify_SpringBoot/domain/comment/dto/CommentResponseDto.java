package Cook.Cookify_SpringBoot.domain.comment.dto;

import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private String  membername;

    public CommentResponseDto(Long id, String content, String  membername) {
        this.id = id;
        this.content = content;
        this.membername = membername;
    }
}
