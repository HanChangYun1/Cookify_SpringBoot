package Cook.Cookify_SpringBoot.domain.heart.service;

import Cook.Cookify_SpringBoot.domain.heart.dto.HeartAlarmDto;
import Cook.Cookify_SpringBoot.domain.heart.dto.HeartCountDto;
import Cook.Cookify_SpringBoot.domain.heart.dto.HeartRecipeDto;
import Cook.Cookify_SpringBoot.domain.heart.entity.Heart;
import Cook.Cookify_SpringBoot.domain.heart.repository.HeartRepository;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeException;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeExceptionType;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HeartServiceImpl implements HeartService{

    private final RecipeRepository recipeRepository;
    private final HeartRepository heartRepository;
    private final GoogleMemberRepository googleMemberRepository;
    private final HttpSession httpSession;

    @Transactional
    public HeartAlarmDto handleHeart(Long recipeId){
        String email = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember member = googleMemberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));
        log.info("boolean:{}", heartRepository.existsByMemberAndRecipe(member,recipe));
        if(!heartRepository.existsByMemberAndRecipe(member,recipe)){
            recipe.setHeartCount(recipe.getHeartCount() + 1);
            Heart heart = heartRepository.save(Heart.createHeart(member, recipe));

            return HeartAlarmDto.builder()
                    .memberName(heart.getMember().getName())
                    .recipeMemberName(heart.getRecipe().getMember().getName())
                    .recipeId(heart.getRecipe().getId())
                    .recipeName(heart.getRecipe().getTitle()).build();
        }else {
            recipe.setHeartCount(recipe.getHeartCount() - 1);
            heartRepository.deleteByMemberAndRecipe(member,recipe);
            return HeartAlarmDto.builder().build();
        }
    }

    public HeartCountDto getHeartCount(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        return HeartCountDto.builder().heartCount(recipe.getHeartCount()).build();
    }

    public List<HeartRecipeDto> getMyRecipe(){
        String email = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember member = googleMemberRepository.findByEmail(email).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        List<Heart> recipes = heartRepository.findAllByMember(member);
        List<HeartRecipeDto> recipeDto = recipes.stream().map(h -> HeartRecipeDto.builder()
                                                                .id(h.getId())
                                                                .title(h.getRecipe().getTitle())
                                                                .thumbnail(h.getRecipe().getThumbnail())
                                                                .heartCount(h.getRecipe().getHeartCount())
                                                                .build())
                                                            .collect(Collectors.toList());
        return recipeDto;
    }
}
