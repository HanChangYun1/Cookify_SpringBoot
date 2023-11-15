package Cook.Cookify_SpringBoot.domain.recipe.service;

import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;
    private final GoogleMemberRepository memberRepository;

    @Transactional
    public Recipe saveRecipe(RecipeRequestDto dto){
        String loginUserEmail = SecurityUtil.getLoginUserEmail();
        GoogleMember member = memberRepository.findByEmail(loginUserEmail).orElse(null);

        Recipe recipe = Recipe.createRecipe(member, dto.getTitle(), dto.getContent());
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

    public Recipe findOne(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        recipe.setRecipeCount(recipe.getRecipeCount() + 1);
        return  recipe;}
}
