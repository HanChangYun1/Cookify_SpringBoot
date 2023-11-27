package Cook.Cookify_SpringBoot.domain.follow;

import Cook.Cookify_SpringBoot.domain.follow.dto.FollowResponseDto;
import Cook.Cookify_SpringBoot.domain.follow.service.FollowService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FollowResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    private final FollowService followService;

    @Autowired
    public FollowResolver(FollowService followService) {
        this.followService = followService;
    }

    public void addFollow(Long memberId) {
        followService.addFollow(memberId);
    }

    public FollowResponseDto getFollow() {
        return followService.getFollow();
    }
}

