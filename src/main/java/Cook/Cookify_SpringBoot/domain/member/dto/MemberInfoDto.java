package Cook.Cookify_SpringBoot.domain.member.dto;

import Cook.Cookify_SpringBoot.domain.entity.GoogleMember;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {

    private String name;
    private String picture;
    private String email;



    @Builder
    public MemberInfoDto(GoogleMember member) {
        this.name = member.getName();
        this.picture = member.getPicture();
        this.email = member.getEmail();
    }
}
