package Cook.Cookify_SpringBoot.domain.heart.repository;

import Cook.Cookify_SpringBoot.domain.heart.entity.Heart;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    boolean existsByMemberAndRecipe(GoogleMember member, Recipe recipe);

    void deleteByMemberAndRecipe(GoogleMember member, Recipe recipe);

    @Query("select h from Heart h left join fetch h.recipe where h.member = :member")
    List<Heart> findAllByMember(@Param("member") GoogleMember member);
}
