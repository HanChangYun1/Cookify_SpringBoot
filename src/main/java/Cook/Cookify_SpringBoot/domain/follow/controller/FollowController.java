package Cook.Cookify_SpringBoot.domain.follow.controller;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowAlarmDto;
import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;
import Cook.Cookify_SpringBoot.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{memberId}")
    public ResponseEntity<FollowAlarmDto> addFollow(@PathVariable("memberId") Long memberid){
        FollowAlarmDto dto = followService.addFollow(memberid);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteFollow(@PathVariable("memberId") Long memberId){
        followService.deleteFollow(memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public ResponseEntity<FollowResponseDto> getMyFollow(){
        FollowResponseDto follow = followService.getMyFollow();
        return ResponseEntity.ok().body(follow);
    }
}
