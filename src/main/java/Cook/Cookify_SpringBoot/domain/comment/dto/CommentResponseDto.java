package Cook.Cookify_SpringBoot.domain.comment.dto;

import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private GoogleMember member;

    private boolean isRemoved;

    public CommentResponseDto(Long id, String content, GoogleMember member, boolean isRemoved) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.isRemoved = isRemoved;
    }
}
