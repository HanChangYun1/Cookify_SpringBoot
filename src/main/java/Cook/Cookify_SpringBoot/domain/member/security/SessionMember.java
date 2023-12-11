package Cook.Cookify_SpringBoot.domain.member.security;

import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private String email;

    public SessionMember(GoogleMember member){
        this.email = member.getEmail();
    }
}
