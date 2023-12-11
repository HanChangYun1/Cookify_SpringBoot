package Cook.Cookify_SpringBoot.domain.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberResponseDto {
    private String email;
    private String name;
    private String picture;

    @Builder
    public MemberResponseDto(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }
}
