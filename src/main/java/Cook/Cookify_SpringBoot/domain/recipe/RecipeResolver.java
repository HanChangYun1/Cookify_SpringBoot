package Cook.Cookify_SpringBoot.domain.recipe;

import Cook.Cookify_SpringBoot.domain.recipe.dto.BriefRecipeDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeDetailDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.service.RecipeService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecipeResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final RecipeService recipeService;

    @Autowired
    public RecipeResolver(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public Recipe saveRecipe(RecipeRequestDto dto) {
        return recipeService.saveRecipe(dto);
    }

    public Recipe updateRecipe(Long id, RecipeRequestDto dto) {
        return recipeService.updateRecipe(id, dto);
    }

    public void deleteRecipe(Long id) {
        recipeService.deleteRecipe(id);
    }

    public List<BriefRecipeDto> findRecipes() {
        return recipeService.findRecipes();
    }

    public RecipeDetailDto findOne(Long recipeId) {
        return recipeService.findOne(recipeId);
    }
}

