package Cook.Cookify_SpringBoot.domain.heart.service;

import Cook.Cookify_SpringBoot.domain.heart.Heart;
import Cook.Cookify_SpringBoot.domain.heart.dto.HeartRecipeDto;
import Cook.Cookify_SpringBoot.domain.heart.repository.HeartRepository;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface HeartService {
    void addHeart(Long recipeId);

    List<HeartRecipeDto> getHeartRecipe();

}
