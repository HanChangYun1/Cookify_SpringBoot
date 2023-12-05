package Cook.Cookify_SpringBoot.domain.member.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
public class MemberUpdateRequest {
    private String name;
    private MultipartFile image;

    @Builder
    public MemberUpdateRequest(String name, MultipartFile image) {
        this.name = name;
        this.image = image;
    }
}
