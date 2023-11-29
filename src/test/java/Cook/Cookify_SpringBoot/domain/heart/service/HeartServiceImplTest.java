package Cook.Cookify_SpringBoot.domain.heart.service;

import Cook.Cookify_SpringBoot.domain.heart.entity.Heart;
import Cook.Cookify_SpringBoot.domain.heart.repository.HeartRepository;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.entity.Role;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

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
        Recipe recipe = Recipe.createRecipe(member, new RecipeRequestDto("tt", "tt", "tt", "tt", "tt"));
        Recipe saveRecipe = recipeRepository.save(recipe);

        //when
//        recipe.setHeartCount(recipe.getHeartCount() + 1);
        recipeRepository.findById(recipe.getId()).orElse(null).setHeartCount(recipe.getHeartCount() + 1);
        Heart heart = heartRepository.save(Heart.createHeart(member, recipe));
        recipeRepository.flush();
        heartRepository.flush();

        //then
        Assertions.assertEquals(member, heartRepository.findById(heart.getId()).orElse(null).getMember());
        Assertions.assertEquals(recipe, heartRepository.findById(heart.getId()).orElse(null).getRecipe());
        Assertions.assertEquals(1, recipeRepository.findById(recipe.getId()).orElse(null).getHeartCount());
    }
}