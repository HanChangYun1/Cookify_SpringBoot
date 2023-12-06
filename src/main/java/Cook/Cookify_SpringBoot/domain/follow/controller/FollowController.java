package Cook.Cookify_SpringBoot.domain.follow.controller;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;
import Cook.Cookify_SpringBoot.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{memberId}")
    public ResponseEntity handlingFollow(@PathVariable("memberId") Long memberid){
        followService.handlingFollow(memberid);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<FollowResponseDto> getMyFollow(){
        FollowResponseDto follow = followService.getMyFollow();
        return ResponseEntity.ok().body(follow);
    }
}
