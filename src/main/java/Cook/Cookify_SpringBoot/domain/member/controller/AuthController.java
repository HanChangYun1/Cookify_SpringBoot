package Cook.Cookify_SpringBoot.domain.member.controller;

import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
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

    @Autowired
    public AuthController(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @GetMapping("/user")
    public SessionMember getUser(@AuthenticationPrincipal GoogleMember user) {
        return new SessionMember(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@AuthenticationPrincipal GoogleMember user) {
        // 로그인 로직을 수행하고 세션에 사용자 정보를 저장
        httpSession.setAttribute("user", new SessionMember(user));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        // 로그아웃 로직을 수행하고 세션에서 사용자 정보를 제거
        session.invalidate();
        return ResponseEntity.ok().build();
    }
}