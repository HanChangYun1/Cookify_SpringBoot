package Cook.Cookify_SpringBoot.global.util;

import Cook.Cookify_SpringBoot.domain.member.security.SessionMember;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.servlet.http.HttpSession;

public class SecurityUtil {
    public static String getLoginUserEmail(HttpSession httpSession){
        SessionMember sessionUser = (SessionMember) httpSession.getAttribute("user");
        String email = sessionUser.getEmail();
        return email;

    }
}
