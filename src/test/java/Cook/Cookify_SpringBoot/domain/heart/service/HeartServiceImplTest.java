package Cook.Cookify_SpringBoot.domain.heart.service;

import Cook.Cookify_SpringBoot.domain.heart.entity.Heart;
import Cook.Cookify_SpringBoot.domain.heart.repository.HeartRepository;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.entity.Role;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeException;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeExceptionType;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootTest
@Transactional
class HeartServiceImplTest {

    @Autowired
    private GoogleMemberRepository googleMemberRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private HeartRepository heartRepository;


    @Test
    public void 좋아요테스트() throws Exception {
        //given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(member, new RecipeRequestDto("tt", Arrays.asList("tt"), Arrays.asList("tt"), Arrays.asList("tt"), "tt"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        //when
        Long recipeId = saveRecipe.getId();
        System.out.println("Recipe ID: " + recipeId);

        Recipe findRecipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));
        System.out.println("Found Recipe: " + findRecipe);
//        Recipe findRecipe = recipeRepository.findById(saveRecipe.getId()).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));
        findRecipe.setHeartCount(findRecipe.getHeartCount() + 1);
        Heart heart = Heart.createHeart(saveMember, saveRecipe);
        Heart saveHeart = heartRepository.save(heart);
        heartRepository.findById(heart.getId()).orElseThrow();
        System.out.println("heart = " + heart);

        //then
        Heart heart1 = heartRepository.findById(heart.getId()).orElseThrow();
        Recipe recipe1 = recipeRepository.findById(saveRecipe.getId()).orElseThrow();
        Assertions.assertEquals(member, heart1.getMember());
        Assertions.assertEquals(recipe, heart1.getRecipe());
        Assertions.assertEquals(1, recipe1.getHeartCount());
    }
}