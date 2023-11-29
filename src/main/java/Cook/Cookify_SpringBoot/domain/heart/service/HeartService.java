package Cook.Cookify_SpringBoot.domain.heart.service;

import Cook.Cookify_SpringBoot.domain.heart.dto.HeartRecipeDto;

import java.util.List;


public interface HeartService {
    void addHeart(Long recipeId);

    List<HeartRecipeDto> getHeartRecipe();

}
