package Cook.Cookify_SpringBoot.domain.recipe.controller;

import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeDocsDetailDto;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.entity.RecipeDocs;
import Cook.Cookify_SpringBoot.domain.recipe.dto.BriefRecipeDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeDetailDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeException;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeExceptionType;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeDocsRepository;
import Cook.Cookify_SpringBoot.domain.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final RecipeDocsRepository recipeDocsRepository;

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeRequestDto dto){
        Recipe createRecipe = recipeService.saveRecipe(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createRecipe);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("recipeId") Long recipeId, @RequestBody RecipeRequestDto dto){
        Recipe updateRecipe = recipeService.updateRecipe(recipeId, dto);
        return ResponseEntity.ok(updateRecipe);
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("recipeId") Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<BriefRecipeDto> getRecipes(){
        return recipeService.findRecipes();
    }

    @GetMapping("/recipe_docs")
    public List<BriefRecipeDto> getRecipeDocs() {
        List<RecipeDocs> recipes = recipeDocsRepository.findAll(PageRequest.of(0, 20)).getContent();
        List<BriefRecipeDto> recipeDtos = recipes.stream().map(m -> new BriefRecipeDto(m.getId(), m.getTitle(), m.getThumbnail()))
                .collect(Collectors.toList());
        return recipeDtos;
    }

    @GetMapping("/recipe_docs/{recipeId}")
    public RecipeDocsDetailDto getRecipeDocsDetail(@PathVariable("recipeId") Long recipeId){
        RecipeDocs recipe = recipeDocsRepository.findById(recipeId).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));
        return new RecipeDocsDetailDto(recipe.getTitle(), recipe.getIngredients(), recipe.getIngredients2(), recipe.getSteps(), recipe.getThumbnail());
    }

    @GetMapping("/{recipeId}")
    public RecipeDetailDto getRecipeDetail(@PathVariable("recipeId") Long recipeId){
        return recipeService.findOne(recipeId);
    }

}
