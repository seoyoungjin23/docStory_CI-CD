package com.ky.docstory.auth;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.UserRepository;
import com.ky.docstory.service.file.FileService;
import java.util.Optional;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final FileService fileService;

    public CustomOAuth2UserService(UserRepository userRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2UserDto userInfo = OAuth2UserDto.from(registrationId, oAuth2User.getAttributes());

        Optional<User> existUser = userRepository.findByProviderId(userInfo.getProviderId());

        User user;
        if (existUser.isPresent()) {
            user = existUser.get();
        } else {
            try {
                user = fileService.uploadProfileImage(userInfo.getProfileImage(), userInfo.getProviderId(), userInfo.getNickname());
            } catch (Exception e) {
                throw new BusinessException(DocStoryResponseCode.FILE_UPLOAD_FAILED);
            }
        }

        return new CustomOAuth2User(oAuth2User, user.getProviderId(), user.getNickname(), user.getProfilePath());
    }
}
