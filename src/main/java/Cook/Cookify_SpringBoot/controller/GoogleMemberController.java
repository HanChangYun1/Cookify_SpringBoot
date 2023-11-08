package Cook.Cookify_SpringBoot.controller;

import Cook.Cookify_SpringBoot.security.SessionMember;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class GoogleMemberController {

    @GetMapping("/test")
    public String getTest(Authentication authentication){
        System.out.println(authentication);

        String userName = authentication.getName();
        return "Hello, " + userName + "? This is a test";
    }

    @GetMapping("/userInfo")
    public String userInfo(@AuthenticationPrincipal SessionMember sessionMember, Model model) {
        model.addAttribute("user", sessionMember);
        return "userInfo";
    }
}
