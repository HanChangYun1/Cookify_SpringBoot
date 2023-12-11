package Cook.Cookify_SpringBoot.domain.recipe.controller;

import Cook.Cookify_SpringBoot.domain.recipe.dto.*;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.entity.RecipeDocs;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeRequestDto dto) {
        Recipe createRecipe = recipeService.saveRecipe(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createRecipe);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<Void> updateRecipe(@PathVariable("recipeId") Long recipeId, @RequestBody RecipeRequestDto dto){
        Recipe updateRecipe = recipeService.updateRecipe(recipeId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable("recipeId") Long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Recipe> getTestRecipes(){
        return recipeService.findTestRecipes();
    }

    @GetMapping("/brief")
    public List<BriefRecipeDto> getRecipes(){
        return recipeService.findRecipes();
    }

    @GetMapping("/recipe_docs")
    public List<BriefRecipeDto> getRecipeDocs(@RequestParam(defaultValue = "0") String page) {
        int pageNum = Integer.parseInt(page);
        List<RecipeDocs> recipes = recipeDocsRepository.findAll(PageRequest.of(pageNum, 20)).getContent();
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

    @GetMapping("/myRecipe")
    public List<BriefRecipeDto> getMyRecipes(){
        return recipeService.findAllByMember();
    }

    @GetMapping("/all")
    public List<RecipeAndDocsDto> getAllRecipeAndDocs(){
        return recipeService.findAllRecipeAndDocs();
    }

    @PostMapping("/image")
    public ResponseEntity<String> imageUpload(@RequestParam("file")MultipartFile file) throws IOException{
            String imageUrl = recipeService.imageUpload(file);
            return ResponseEntity.ok(imageUrl);
    }

}
