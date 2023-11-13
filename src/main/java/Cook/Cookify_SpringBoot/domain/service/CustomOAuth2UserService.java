package Cook.Cookify_SpringBoot.domain.service;

import Cook.Cookify_SpringBoot.domain.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.security.OAuthAttributes;
import Cook.Cookify_SpringBoot.domain.security.SessionMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final GoogleMemberRepository googleMemberRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        GoogleMember member = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionMember(member));    // 세션에 저장

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private GoogleMember saveOrUpdate(OAuthAttributes attributes) {
        GoogleMember member = googleMemberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return googleMemberRepository.save(member);
    }

}
