package Cook.Cookify_SpringBoot.domain.member.controller;

import Cook.Cookify_SpringBoot.domain.member.dto.MemberInfoDto;
import Cook.Cookify_SpringBoot.domain.member.dto.MemberResponseDto;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.security.SessionMember;
import Cook.Cookify_SpringBoot.domain.member.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;


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
    public ResponseEntity<MemberResponseDto> getUser() {
        MemberResponseDto dto = customOAuth2UserService.getUser();
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@AuthenticationPrincipal GoogleMember user) {
        try {
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }
            httpSession.setAttribute("user", new SessionMember(user));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        try {
            httpSession.invalidate();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
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
