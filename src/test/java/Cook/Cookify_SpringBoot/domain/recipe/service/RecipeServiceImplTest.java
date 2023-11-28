package Cook.Cookify_SpringBoot.domain.recipe.service;

import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.entity.Role;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.dto.BriefRecipeDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeDetailDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeException;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeExceptionType;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
class RecipeServiceImplTest {

    @Autowired
    private GoogleMemberRepository googleMemberRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeService recipeService;

    @Test
    public void 레시피작성() throws Exception {
        //given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(member, new RecipeRequestDto("tt", "tt", "tt", "tt", "tt"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        //when
        Recipe findRecipe = recipeRepository.findById(saveRecipe.getId()).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        //then
        Assertions.assertEquals(findRecipe.getMember().getId(), member.getId());
        Assertions.assertEquals(findRecipe.getMember(), member);
    }

    @Test
    public void 레시피수정() {
        // given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(member, new RecipeRequestDto("tt", "tt", "tt", "tt", "tt"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        // when
        RecipeRequestDto updatedDto = new RecipeRequestDto("updatedTitle", "updatedIng1", "updatedIng2", "updatedStep", "updatedThumbnail");
        saveRecipe.update(updatedDto);
        Recipe updatedRecipe = recipeRepository.findById(saveRecipe.getId()).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        // thens
        Assertions.assertEquals(updatedRecipe.getTitle(), updatedDto.getTitle());
        Assertions.assertEquals(updatedRecipe.getIngredients(), updatedDto.getIngredients());
        Assertions.assertEquals(updatedRecipe.getIngredients2(), updatedDto.getIngredients2());
        Assertions.assertEquals(updatedRecipe.getSteps(), updatedDto.getSteps());
        Assertions.assertEquals(updatedRecipe.getThumbnail(), updatedDto.getThumbnail());
    }

    @Test
    public void 레시피삭제() {
        // given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(member, new RecipeRequestDto("tt", "tt", "tt", "tt", "tt"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        // when
        recipeRepository.delete(saveRecipe);

        // then
        Assertions.assertThrows(RecipeException.class, () -> recipeRepository.findById(saveRecipe.getId()).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe)));
    }

    @Test
    public void 레시피목록조회() {
        // given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe1 = Recipe.createRecipe(member, new RecipeRequestDto("title1", "ing1", "ing2", "step1", "thumb1"));
        Recipe recipe2 = Recipe.createRecipe(member, new RecipeRequestDto("title2", "ing3", "ing4", "step2", "thumb2"));
        recipeRepository.save(recipe1);
        recipeRepository.save(recipe2);

        // when
        List<BriefRecipeDto> recipes = recipeService.findRecipes();

        // then
        Assertions.assertEquals(recipes.size(), 2);
    }

    @Test
    public void 레시피상세조회() {
        // given
        GoogleMember member = GoogleMember.builder().name("test").email("test@gmail.com").picture("https://test.com/test.jpg").role(Role.USER).build();
        GoogleMember saveMember = googleMemberRepository.save(member);
        Recipe recipe = Recipe.createRecipe(member, new RecipeRequestDto("title", "ing1", "ing2", "step", "thumb"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        // when
        RecipeDetailDto recipeDetail = recipeService.findOne(saveRecipe.getId());

        // then
        Assertions.assertEquals(recipeDetail.getTitle(), saveRecipe.getTitle());
        Assertions.assertEquals(recipeDetail.getIngredients(), saveRecipe.getIngredients());
        Assertions.assertEquals(recipeDetail.getIngredients2(), saveRecipe.getIngredients2());
        Assertions.assertEquals(recipeDetail.getSteps(), saveRecipe.getSteps());
        Assertions.assertEquals(recipeDetail.getThumbnail(), saveRecipe.getThumbnail());
    }

}