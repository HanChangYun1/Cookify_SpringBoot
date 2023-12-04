package Cook.Cookify_SpringBoot.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentRequestDto {
    private String content;

    @JsonCreator
    public CommentRequestDto(@JsonProperty("content") String content) {
        this.content = content;
    }
}
