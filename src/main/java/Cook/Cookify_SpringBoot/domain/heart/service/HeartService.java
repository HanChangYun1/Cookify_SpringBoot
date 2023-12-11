package Cook.Cookify_SpringBoot.domain.heart.service;

import Cook.Cookify_SpringBoot.domain.heart.dto.HeartCountDto;
import Cook.Cookify_SpringBoot.domain.heart.dto.HeartRecipeDto;
import Cook.Cookify_SpringBoot.domain.heart.entity.Heart;

import java.util.List;


public interface HeartService {
    Heart addHeart(Long recipeId);

    void deleteHeart(Long recipeId);

    HeartCountDto getHeartCount(Long recipeId);

    List<HeartRecipeDto> getMyRecipe();


}
