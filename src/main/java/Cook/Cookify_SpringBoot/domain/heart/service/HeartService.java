package Cook.Cookify_SpringBoot.domain.heart.service;

import Cook.Cookify_SpringBoot.domain.heart.dto.HeartAlarmDto;
import Cook.Cookify_SpringBoot.domain.heart.dto.HeartCountDto;
import Cook.Cookify_SpringBoot.domain.heart.dto.HeartRecipeDto;

import java.util.List;


public interface HeartService {
    HeartAlarmDto handleHeart(Long recipeId);

    HeartCountDto getHeartCount(Long recipeId);

    List<HeartRecipeDto> getMyRecipe();


}
