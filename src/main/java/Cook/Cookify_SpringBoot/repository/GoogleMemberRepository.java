package Cook.Cookify_SpringBoot.repository;

import Cook.Cookify_SpringBoot.entity.GoogleMember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoogleMemberRepository extends JpaRepository<GoogleMember, Long> {
    Optional<GoogleMember> findByEmail(String email);
}
