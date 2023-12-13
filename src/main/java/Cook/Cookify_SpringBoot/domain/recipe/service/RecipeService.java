package Cook.Cookify_SpringBoot.domain.recipe.service;

import Cook.Cookify_SpringBoot.domain.recipe.dto.BriefRecipeDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeAndDocsDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeDetailDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface RecipeService {
    Recipe saveRecipe(RecipeRequestDto dto);

    Recipe updateRecipe(Long id, RecipeRequestDto dto);

    void deleteRecipe(Long id);

    List<BriefRecipeDto> findRecipes(String page);

    RecipeDetailDto findOne(Long recipeId);

    List<BriefRecipeDto> findAllByMember();

    List<RecipeAndDocsDto> findAllByKeyword(String keyword, int pageNum);

    String imageUpload(MultipartFile file) throws IOException;

}
