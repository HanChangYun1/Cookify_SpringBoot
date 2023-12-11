package Cook.Cookify_SpringBoot.domain.comment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CommentRequestDto {
    @NotNull(message = "Ingredients cannot be null")
    private String content;

    @JsonCreator
    public CommentRequestDto(@JsonProperty("content") String content) {
        this.content = content;
    }
}
