package Cook.Cookify_SpringBoot.domain.member.controller;

import Cook.Cookify_SpringBoot.domain.member.dto.MemberInfoDto;
import Cook.Cookify_SpringBoot.domain.member.dto.MemberUpdateRequest;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.security.SessionMember;
import Cook.Cookify_SpringBoot.domain.member.service.CustomOAuth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
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
    public ResponseEntity<SessionMember> getUser() {
        SessionMember sessionUser = (SessionMember) httpSession.getAttribute("user");
        return ResponseEntity.ok().body(sessionUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@AuthenticationPrincipal GoogleMember user) {
        try {
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }

            // 로그인 로직을 수행하고 세션에 사용자 정보를 저장
            httpSession.setAttribute("user", new SessionMember(user));


            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log them)
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        try {
            // 세션 무효화
            httpSession.invalidate();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log them)
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody MemberInfoDto dto) {
        customOAuth2UserService.update(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/image")
    public ResponseEntity<String> imageUpload(@RequestParam("file")MultipartFile file) throws IOException{
        String imageUrl = customOAuth2UserService.imageUpload(file);
        return ResponseEntity.ok(imageUrl);
    }
}
