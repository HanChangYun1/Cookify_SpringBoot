package Cook.Cookify_SpringBoot.domain.comment.dto;

import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private String  memberName;

    private boolean isRemoved;

    public CommentResponseDto(Long id, String content, String memberName, boolean isRemoved) {
        this.id = id;
        this.content = content;
        this.memberName = memberName;
        this.isRemoved = isRemoved;
    }
}
