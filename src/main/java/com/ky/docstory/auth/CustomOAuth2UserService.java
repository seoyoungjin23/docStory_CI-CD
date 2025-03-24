package com.ky.docstory.auth;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.dto.ProfileImage;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.UserRepository;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public CustomOAuth2UserService(UserRepository userRepository, S3Template s3Template) {
        this.userRepository = userRepository;
        this.s3Template = s3Template;
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
            ProfileImage profileImage;
            try {
                profileImage = uploadProfileImage(userInfo.getProfileImage());
            } catch (IOException e) {
                throw new BusinessException(DocStoryResponseCode.FILE_UPLOAD_FAILED);
            }

            user = User.builder()
                    .providerId(userInfo.getProviderId())
                    .nickname(userInfo.getNickname())
                    .profilePath(profileImage.getFilePath())
                    .profileFileName(profileImage.getSaveFilename())
                    .build();

            user = userRepository.save(user);
        }

        return new CustomOAuth2User(oAuth2User, user.getProviderId(), user.getNickname(), user.getProfilePath());
    }

    private ProfileImage uploadProfileImage(String socialFileUrl) throws IOException {
        URL url = new URL(socialFileUrl);
        String fileType = "jpeg";

        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        String saveFilename = UUID.randomUUID() + "." + fileType;
        String filePath = "profileImage/" + dateDir + "/" + saveFilename;

        try (InputStream inputStream = url.openStream()) {
            s3Template.upload(bucketName, filePath, inputStream);
        }

        return new ProfileImage(filePath, saveFilename);
    }
}
