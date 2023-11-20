package Cook.Cookify_SpringBoot.domain.recipe.service;

import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeException;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeExceptionType;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.shaded.json.JSONArray;
import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;
    private final GoogleMemberRepository memberRepository;

    @Transactional
    public Recipe saveRecipe(RecipeRequestDto dto){
        String loginUserEmail = SecurityUtil.getLoginUserEmail();
        GoogleMember member = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));

        Recipe recipe = Recipe.createRecipe(member, dto);
        return recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe updateRecipe(Long id, RecipeRequestDto dto){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        if (!recipe.getMember().getId().equals(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail()).get().getId())){
            throw new RecipeException(RecipeExceptionType.NOT_AUTHORITY_UPDATE_Recipe);
        }

        recipe.update(dto);
        return recipe;
    }

    @Transactional
    public void deleteRecipe(Long id){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));

        if (!recipe.getMember().getId().equals(memberRepository.findByEmail(SecurityUtil.getLoginUserEmail()).get().getId())){
            throw new RecipeException(RecipeExceptionType.NOT_AUTHORITY_DELETE_Recipe);
        }

        recipeRepository.deleteById(id);
    }

    public List<Recipe> findRecipes(){ return  recipeRepository.findAll();}

    public Recipe findOne(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId).orElse(null);
        recipe.setRecipeCount(recipe.getRecipeCount() + 1);
        return  recipe;
    }
}
