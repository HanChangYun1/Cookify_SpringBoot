package Cook.Cookify_SpringBoot.domain.heart.repository;

import Cook.Cookify_SpringBoot.domain.heart.Heart;
import Cook.Cookify_SpringBoot.domain.member.GoogleMember;
import Cook.Cookify_SpringBoot.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    boolean existsByMemberAndRecipe(GoogleMember member, Recipe recipe);

    void deleteByMemberAndRecipe(GoogleMember member, Recipe recipe);

    List<Heart> findByMember(GoogleMember member);
}
