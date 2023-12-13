package Cook.Cookify_SpringBoot.domain.recipe.service;

import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeAndDocsDto;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import Cook.Cookify_SpringBoot.domain.recipe.dto.BriefRecipeDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeDetailDto;
import Cook.Cookify_SpringBoot.domain.recipe.dto.RecipeRequestDto;
import Cook.Cookify_SpringBoot.domain.recipe.entity.RecipeDocs;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeException;
import Cook.Cookify_SpringBoot.domain.recipe.exception.RecipeExceptionType;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeDocsRepository;
import Cook.Cookify_SpringBoot.domain.recipe.repository.RecipeRepository;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{
    private final RecipeRepository recipeRepository;
    private final RecipeDocsRepository recipeDocsRepository;
    private final GoogleMemberRepository memberRepository;
    private final HttpSession httpSession;
    private final Storage storage;
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Transactional
    public Recipe saveRecipe(RecipeRequestDto dto)  {
        String loginUserEmail = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember member = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));
        Recipe recipe = Recipe.createRecipe(member, dto);

        return recipeRepository.save(recipe);
    }

    @Transactional
    public Recipe updateRecipe(Long id, RecipeRequestDto dto) {
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

    public List<BriefRecipeDto> findRecipes(String page){
        int pageNum = Integer.parseInt(page);
        List<Recipe> recipes = recipeRepository.findAllWithMemberComment(PageRequest.of(pageNum ,20));
        List<BriefRecipeDto> collects = recipes.stream().map(r -> new BriefRecipeDto(r.getId(), r.getTitle(), r.getThumbnail())).collect(Collectors.toList());
        return collects;
    }

    public RecipeDetailDto findOne(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RecipeException(RecipeExceptionType.NOT_FOUND_Recipe));
        RecipeDetailDto recipeDto = new RecipeDetailDto(recipe.getTitle(), recipe.getIngredients(), recipe.getIngredients2(), recipe.getSteps(), recipe.getThumbnail(), recipe.getComments());
        return  recipeDto;
    }

    public List<BriefRecipeDto> findAllByMember(){
        String loginUserEmail = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember googleMember = memberRepository.findByEmail(loginUserEmail).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));
        List<Recipe> recipes = recipeRepository.findAllByMember(googleMember.getId());
        List<BriefRecipeDto> collects = recipes.stream().map(r -> new BriefRecipeDto(r.getId(), r.getTitle(), r.getThumbnail())).collect(Collectors.toList());
        return collects;
    }


    public List<RecipeAndDocsDto> findAllByKeyword(String keyword) {
        List<Recipe> recipes = recipeRepository.findAllByTitleContaining(keyword, PageRequest.of(0, 10, Sort.Direction.DESC));
        List<RecipeDocs> recipeDocs = recipeDocsRepository.findAllByTitleContaining(keyword, PageRequest.of(0, 10, Sort.Direction.DESC));
        List<RecipeAndDocsDto> collects = new ArrayList<>();

        for (Recipe recipe : recipes){
            RecipeAndDocsDto dto = new RecipeAndDocsDto();
            dto.setRecipeId(recipe.getId());
            dto.setRecipeTitle(recipe.getTitle());
            dto.setRecipeThumbnail(recipe.getThumbnail());
            collects.add(dto);
        }

        for (RecipeDocs recipe: recipeDocs){
            RecipeAndDocsDto dto = new RecipeAndDocsDto();
            dto.setRecipeDocsId(recipe.getId());
            dto.setRecipeTitle(recipe.getTitle());
            dto.setRecipeThumbnail(recipe.getThumbnail());
            collects.add(dto);
        }
        return  collects;
    }
    public List<RecipeAndDocsDto> findAllByKeyword2(String keyword, int pageNum) {
        List<Object[]> allTitlesContaining = recipeRepository.findAllTitlesContaining(keyword, PageRequest.of(pageNum, 20));
        List<RecipeAndDocsDto> collects = new ArrayList<>();

        for (Object[] object : allTitlesContaining){
            RecipeAndDocsDto dto = new RecipeAndDocsDto();
            if(object[0] != null){
                dto.setRecipeId(((Number) object[0]).longValue());
            }else {
                dto.setRecipeDocsId(((Number) object[1]).longValue());
            }
            dto.setRecipeTitle((String) object[2]);
            dto.setRecipeThumbnail((String) object[3]);
            collects.add(dto);
        }

        return  collects;
    }

    @Transactional
    public String imageUpload(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String ext = file.getContentType();

        // Cloud에 이미지 업로드
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                file.getInputStream()
        );

        String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + uuid;

        return imageUrl;
    }
}
