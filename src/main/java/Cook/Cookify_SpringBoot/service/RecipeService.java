package Cook.Cookify_SpringBoot.service;

import Cook.Cookify_SpringBoot.controller.RecipeRequestDto;
import Cook.Cookify_SpringBoot.entity.Recipe;
import Cook.Cookify_SpringBoot.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Transactional
    public Recipe saveRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe updateRecipe(Long id, RecipeRequestDto dto){
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        recipe.update(dto);
        return recipe;
    }

    @Transactional
    public void deleteRecipe(Long id){
        recipeRepository.deleteById(id);
    }

    public List<Recipe> findRecipes(){ return  recipeRepository.findAll();}

    public Optional<Recipe> findOne(Long recipeId){ return  recipeRepository.findById(recipeId);}
}
