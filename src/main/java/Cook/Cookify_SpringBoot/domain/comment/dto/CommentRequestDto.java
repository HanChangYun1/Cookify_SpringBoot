package Cook.Cookify_SpringBoot.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;

    @JsonCreator
    public CommentRequestDto(@JsonProperty("content") String content) {
        this.content = content;
    }
}
