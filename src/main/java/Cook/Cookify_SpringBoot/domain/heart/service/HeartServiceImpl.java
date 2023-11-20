package Cook.Cookify_SpringBoot.domain.heart.service;

import Cook.Cookify_SpringBoot.domain.heart.Heart;
import Cook.Cookify_SpringBoot.domain.heart.dto.HeartRecipeDto;
import Cook.Cookify_SpringBoot.domain.heart.repository.HeartRepository;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeException;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeExceptionType;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HeartServiceImpl implements HeartService{

    private RecipeRepository recipeRepository;
    private HeartRepository heartRepository;
    private GoogleMemberRepository googleMemberRepository;

    @Transactional
    public void addHeart(Long recipeId){
        String email = SecurityUtil.getLoginUserEmail();
        GoogleMember member = googleMemberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        if (!heartRepository.existsByMemberAndRecipe(member, recipe)){
            recipe.setHeartCount(recipe.getHeartCount() + 1);
            heartRepository.save(Heart.createHeart(member, recipe));
        }else {
            recipe.setHeartCount(recipe.getHeartCount() - 1);
            heartRepository.deleteByMemberAndRecipe(member, recipe);
        }
    }

    public List<HeartRecipeDto> getHeartRecipe(){
        String email = SecurityUtil.getLoginUserEmail();
        GoogleMember member = googleMemberRepository.findByEmail(email).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        List<Heart> recipes = heartRepository.findAllByMember(member);
        List<HeartRecipeDto> recipeDto = recipes.stream().map(h -> new HeartRecipeDto(h.getRecipe().getTitle(), h.getRecipe().getThumbnail())).collect(Collectors.toList());
        return recipeDto;
    }
}
