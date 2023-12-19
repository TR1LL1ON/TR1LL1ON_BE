package com.ybe.tr1ll1on.security.oauth;

import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.security.model.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        System.out.println(oAuth2User.getAttributes());

        OAuth2Attribute attributes = OAuth2Attribute.of(
                userRequest.getClientRegistration().getRegistrationId(),
                oAuth2User.getAttributes()
        );

        User user = updateOrCreateUser(attributes);

        return createOAuth2User(user, attributes);
    }

    private User updateOrCreateUser(OAuth2Attribute attributes) {
        Optional<User> optionalUser = userRepository.findByEmail(attributes.getEmail());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(attributes.getName());
            existingUser.setPicture(attributes.getPicture());

            return userRepository.save(existingUser);
        }

        User newUser = userRepository.save(attributes.toEntity());
        newUser.setCart(cartRepository.save(
                Cart.builder()
                        .user(newUser)
                        .build()));

        return newUser;
    }

    private OAuth2User createOAuth2User(User user, OAuth2Attribute attributes) {

        return  PrincipalDetails.builder()
                .username(String.valueOf(user.getId()))
                .authority(user.getAuthority().toString())
                .attributes(attributes.getAttributes())
                .build();
    }
}