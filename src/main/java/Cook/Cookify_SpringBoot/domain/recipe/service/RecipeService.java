package Cook.Cookify_SpringBoot.domain.recipe.service;

import Cook.Cookify_SpringBoot.domain.recipe.dto.BriefRecipeDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeDetailDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;

import java.util.List;


public interface RecipeService {
    Recipe saveRecipe(RecipeRequestDto dto);

    Recipe updateRecipe(Long id, RecipeRequestDto dto);

    void deleteRecipe(Long id);

    List<BriefRecipeDto> findRecipes();

    RecipeDetailDto findOne(Long recipeId);

    List<Recipe> findTestRecipes();
}
