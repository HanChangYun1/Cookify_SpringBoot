package Cook.Cookify_SpringBoot.domain.member.security;

import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
@Getter
public class OAuthAttributes {

    private final String id;
    private final String email;
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String picture;

    @Builder
    public OAuthAttributes(String id,Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture){
        this.id = id;
        this.email = email;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.picture = picture;
    }


    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        // Handle other providers...

        throw new IllegalArgumentException("Unsupported provider: " + registrationId);
    }



        public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
            return OAuthAttributes.builder()
                    .id((String) attributes.get("sub"))
                    .name((String) attributes.get("name"))
                    .email((String) attributes.get("email"))
                    .picture((String) attributes.get("picture"))
                    .attributes(attributes)
                    .nameAttributeKey(userNameAttributeName)
                    .build();
        }



    public GoogleMember toEntity(){
        return GoogleMember.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }

    public String getId() {
        return id;
    }
}
