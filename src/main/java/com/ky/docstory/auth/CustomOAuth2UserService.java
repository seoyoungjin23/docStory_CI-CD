package com.ky.docstory.auth;

import com.ky.docstory.entity.User;
import com.ky.docstory.repository.UserRepository;
import java.util.Optional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserDto userInfo = OAuth2UserDto.from(registrationId, oAuth2User.getAttributes());

        Optional<User> existUser = userRepository.findByProviderId(userInfo.getProviderId());

        User user;
        if (existUser.isPresent()) {
            user = existUser.get();
        } else {
            user = User.builder()
                    .providerId(userInfo.getProviderId())
                    .nickname(userInfo.getNickname())
                    .build();
            logger.info("About to save user: {}", user);

            try {
                user = userRepository.save(user);
                logger.info("After save - user: {}", user);
            } catch (Exception e) {
                logger.error("Failed to save user: {}, Error: {}", user, e.getMessage(), e);
            }
        }

        return new CustomOAuth2User(oAuth2User, user.getProviderId());
    }
}
