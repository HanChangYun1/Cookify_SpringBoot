package Cook.Cookify_SpringBoot.domain.member.controller;

import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.service.CustomOAuth2UserService;
import Cook.Cookify_SpringBoot.domain.member.security.SessionMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000") // CORS 설정
public class AuthController {

    private final HttpSession httpSession;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public AuthController(HttpSession httpSession, CustomOAuth2UserService customOAuth2UserService) {
        this.httpSession = httpSession;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @GetMapping("/user")
    public ResponseEntity<SessionMember> getUser(@AuthenticationPrincipal GoogleMember user) {
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new SessionMember(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@AuthenticationPrincipal GoogleMember user) {
        try {
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }

            // 로그인 로직을 수행하고 세션에 사용자 정보를 저장
            httpSession.setAttribute("user", new SessionMember(user));

            // Save or update user in the database using the service
            customOAuth2UserService.saveOrUpdateUser(user);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log them)
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        try {
            // 로그아웃 로직을 수행하고 세션에서 사용자 정보를 제거
            session.invalidate();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log them)
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }
}
