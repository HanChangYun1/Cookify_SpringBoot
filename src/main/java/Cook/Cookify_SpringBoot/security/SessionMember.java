package Cook.Cookify_SpringBoot.security;

import Cook.Cookify_SpringBoot.entity.GoogleMember;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionMember(GoogleMember member){
        this.name = member.getName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
