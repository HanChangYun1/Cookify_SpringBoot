package Cook.Cookify_SpringBoot.domain.member.service;

import Cook.Cookify_SpringBoot.domain.member.dto.MemberInfoDto;
import Cook.Cookify_SpringBoot.domain.member.dto.MemberResponseDto;
import Cook.Cookify_SpringBoot.domain.member.dto.MemberUpdateRequest;
import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.member.security.OAuthAttributes;
import Cook.Cookify_SpringBoot.domain.member.security.SessionMember;
import Cook.Cookify_SpringBoot.global.GoogleCloudStorageConfig;
import Cook.Cookify_SpringBoot.global.util.SecurityUtil;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final GoogleMemberRepository googleMemberRepository;
    private final HttpSession httpSession;
    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();


        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        GoogleMember member = findOrSave(attributes);

        httpSession.setAttribute("user", new SessionMember(member));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }


    public GoogleMember findOrSave(OAuthAttributes attributes) {
        GoogleMember member = googleMemberRepository.findByEmail(attributes.getEmail())
                .orElse(attributes.toEntity());
        return googleMemberRepository.save(member);
    }

    public void update(MemberInfoDto dto){
        String email = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember member = googleMemberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));


        member.update(dto);
    }

    public String imageUpload(MultipartFile file) throws IOException{

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

    public MemberResponseDto getUser(){
        String email = SecurityUtil.getLoginUserEmail(httpSession);
        GoogleMember member = googleMemberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));

        return MemberResponseDto.builder().email(member.getEmail()).name(member.getName()).picture(member.getPicture()).build();
    }
}
