package Cook.Cookify_SpringBoot.domain.comment.dto;

import Cook.Cookify_SpringBoot.domain.comment.Comment;

public class CommentSaveDto {

    private final String content;

    public CommentSaveDto(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Comment toEntity() {
        return Comment.builder().content(content).build();
    }
}
