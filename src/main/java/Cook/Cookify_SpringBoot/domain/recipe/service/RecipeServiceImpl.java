package Cook.Cookify_SpringBoot.domain.recipe.service;

import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.dto.BriefRecipeDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeDetailDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeException;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeExceptionType;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;
    private final GoogleMemberRepository memberRepository;
    private final HttpSession httpSession;

    @Transactional
    public Recipe saveRecipe(RecipeRequestDto dto){
        String loginUserEmail = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember member = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));

        Recipe recipe = Recipe.createRecipe(member, dto);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe updateRecipe(Long id, RecipeRequestDto dto){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        if (!recipe.getMember().getId().equals(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail(httpSession)).get().getId())){
            throw new RecipeException(RecipeExceptionType.NOT_AUTHORITY_UPDATE_Recipe);
        }

        recipe.update(dto);
        return recipe;
    }

    @Transactional
    public void deleteRecipe(Long id){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        if (!recipe.getMember().getId().equals(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail(httpSession)).get().getId())){
            throw new RecipeException(RecipeExceptionType.NOT_AUTHORITY_DELETE_Recipe);
        }

        recipeRepository.deleteById(id);
    }

    public List<BriefRecipeDto> findRecipes(){
        List<Recipe> recipes = recipeRepository.findAll();
        List<BriefRecipeDto> collects = recipes.stream().map(r -> new BriefRecipeDto(r.getId(), r.getTitle(), r.getThumbnail())).collect(Collectors.toList());
        return collects;
    }

    public RecipeDetailDto findOne(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        RecipeDetailDto recipeDto = new RecipeDetailDto(recipe.getTitle(), recipe.getIngredients(), recipe.getIngredients2(), recipe.getSteps(), recipe.getThumbnail());
        return  recipeDto;
    }
}
