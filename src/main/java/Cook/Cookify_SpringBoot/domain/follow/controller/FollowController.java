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
    public ResponseEntity<FollowAlarmDto> handleFollow(@PathVariable("memberId") Long memberid){
        FollowAlarmDto dto = followService.handleFollow(memberid);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }



    @GetMapping()
    public ResponseEntity<FollowResponseDto> getMyFollow(){
        FollowResponseDto follow = followService.getMyFollow();
        return ResponseEntity.ok().body(follow);
    }
}
