package Cook.Cookify_SpringBoot.domain.heart;

import Cook.Cookify_SpringBoot.domain.heart.dto.HeartRecipeDto;
import Cook.Cookify_SpringBoot.domain.heart.service.HeartService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeartResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final HeartService heartService;

    @Autowired
    public HeartResolver(HeartService heartService) {
        this.heartService = heartService;
    }

    public void addHeart(Long recipeId) {
        heartService.addHeart(recipeId);
    }

    public List<HeartRecipeDto> getHeartRecipe() {
        return heartService.getHeartRecipe();
    }
}

