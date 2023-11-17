package Cook.Cookify_SpringBoot.domain.follow.controller;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;
import Cook.Cookify_SpringBoot.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private FollowService followService;

    @PostMapping("/{memberId}")
    public ResponseEntity addFollow(@PathVariable("memberId") Long memberid){
        followService.addFollow(memberid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<FollowResponseDto> getFollow(){
        FollowResponseDto follow = followService.getFollow();
        return ResponseEntity.ok().body(follow);
    }
}
