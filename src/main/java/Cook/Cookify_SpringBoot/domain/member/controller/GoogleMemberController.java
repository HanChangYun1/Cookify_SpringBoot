package Cook.Cookify_SpringBoot.domain.member.controller;

import Cook.Cookify_SpringBoot.domain.member.security.SessionMember;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoogleMemberController {

    @GetMapping("/userInfo")
    public String userInfo(@AuthenticationPrincipal SessionMember sessionMember, Model model) {
        model.addAttribute("user", sessionMember);
        return "userInfo";
    }
}
