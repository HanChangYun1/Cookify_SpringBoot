package Cook.Cookify_SpringBoot.domain.heart.service;

import Cook.Cookify_SpringBoot.domain.heart.Heart;
import Cook.Cookify_SpringBoot.domain.heart.repository.HeartRepository;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        GoogleMember member = googleMemberRepository.findByEmail(email).orElse(null);

        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        if (!heartRepository.existsByMemberAndRecipe(member, recipe)){
            recipe.setHeartCount(recipe.getHeartCount() + 1);
            heartRepository.save(Heart.createHeart(member, recipe));
        }else {
            recipe.setHeartCount(recipe.getHeartCount() - 1);
            heartRepository.deleteByMemberAndRecipe(member, recipe);
        }
    }

    public List<Heart> getHeartRecipe(){
        String email = SecurityUtil.getLoginUserEmail();
        GoogleMember member = googleMemberRepository.findByEmail(email).orElse(null);

        List<Heart> recipes = heartRepository.findAllByMember(member);
        return recipes;
    }
}
