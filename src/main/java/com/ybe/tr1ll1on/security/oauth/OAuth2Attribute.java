package com.ybe.tr1ll1on.security.oauth;

import com.ybe.tr1ll1on.domain.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

import static com.ybe.tr1ll1on.security.common.Authority.ROLE_USER;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
    private String name;
    private String email;
    private String picture;

    private Map<String, Object> attributes;

    static OAuth2Attribute of(
            String provider, Map<String, Object> attributes
    ) {
        switch (provider) {
            case "google":
                return ofGoogle(attributes);
            case "kakao":
                return ofKakao(attributes);
            case "naver":
                return ofNaver(attributes);
            default:
                throw new RuntimeException("Unsupported provider: " + provider);
        }
    }

    private static OAuth2Attribute ofGoogle(
            Map<String, Object> attributes
    ) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .build();
    }

    private static OAuth2Attribute ofKakao(
            Map<String, Object> attributes
    ) {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .attributes(attributes)
                .build();
    }

    private static OAuth2Attribute ofNaver(
            Map<String, Object> attributes
    ) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .name((String) response.get("nickname"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(attributes)
                .build();
    }

    public User toEntity() {

        return User.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .authority(ROLE_USER)
                .build();
    }
}