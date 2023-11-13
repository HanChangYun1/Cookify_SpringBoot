package Cook.Cookify_SpringBoot.controller;

import Cook.Cookify_SpringBoot.entity.Recipe;
import Cook.Cookify_SpringBoot.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe){
        Recipe createRecipe = recipeService.saveRecipe(recipe);
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
    public List<Recipe> getRecipes(){
        return recipeService.findRecipes();
    }

    @GetMapping("/{recipeId}")
    public Recipe getRecipe(@PathVariable("recipeId") Long id){
        return recipeService.findOne(id).orElse(null);
    }
}
