package Cook.Cookify_SpringBoot.domain.heart.controller;

import Cook.Cookify_SpringBoot.domain.heart.dto.HeartCountDto;
import Cook.Cookify_SpringBoot.domain.heart.dto.HeartRecipeDto;
import Cook.Cookify_SpringBoot.domain.heart.dto.HeartAlarmDto;
import Cook.Cookify_SpringBoot.domain.heart.entity.Heart;
import Cook.Cookify_SpringBoot.domain.heart.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/heart")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/{recipeId}")
    public ResponseEntity<HeartAlarmDto> handleHeart(@PathVariable("recipeId") Long recipeId){
        HeartAlarmDto dto = heartService.handleHeart(recipeId);
        log.info("dto:{}", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/myRecipe")
    public ResponseEntity<List<HeartRecipeDto>> getMyRecipe(){
        List<HeartRecipeDto> recipes = heartService.getMyRecipe();
        return ResponseEntity.ok().body(recipes);
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<HeartCountDto> getHeartCount(@PathVariable("recipeId") Long recipeId){
        HeartCountDto heartCount = heartService.getHeartCount(recipeId);
        return ResponseEntity.ok().body(heartCount);
    }
}
