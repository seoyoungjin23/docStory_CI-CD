package com.ky.docstory.auth;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserDto {

    private String providerId;
    private String nickname;
    private String profileImage;
    private String email;

    public static OAuth2UserDto from(String registrationId, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return fromGoogle(attributes);
        } else if ("kakao".equals(registrationId)) {
            return fromKakao(attributes);
        } else {
            throw new BusinessException(DocStoryResponseCode.UNSUPPORTED_SOCIAL_PROVIDER);
        }
    }

    private static OAuth2UserDto fromGoogle(Map<String, Object> attributes) {
        return OAuth2UserDto.builder()
                .providerId((String) attributes.get("sub"))
                .nickname((String) attributes.get("name"))
                .profileImage((String) attributes.get("picture"))
                .email((String) attributes.get("email"))
                .build();
    }

    private static OAuth2UserDto fromKakao(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return OAuth2UserDto.builder()
                .providerId(attributes.get("id").toString())
                .nickname((String) properties.get("nickname"))
                .profileImage((String) properties.get("profile_image"))
                .email((String) kakaoAccount.get("email"))
                .build();
    }
}
