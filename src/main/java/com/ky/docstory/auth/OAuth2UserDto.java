package com.ky.docstory.auth;

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

    public static OAuth2UserDto from(String registrationId, Map<String, Object> attributes) {
        if ("google".equals(registrationId)) {
            return fromGoogle(attributes);
        } else if ("kakao".equals(registrationId)) {
            return fromKakao(attributes);
        } else {
            throw new IllegalArgumentException("지원하지 않는 소셜 로그인 제공자: " + registrationId);
        }
    }

    private static OAuth2UserDto fromGoogle(Map<String, Object> attributes) {
        return OAuth2UserDto.builder()
                .providerId((String) attributes.get("sub"))
                .nickname((String) attributes.get("name"))
                .build();
    }

    private static OAuth2UserDto fromKakao(Map<String, Object> attributes) {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        return OAuth2UserDto.builder()
                .providerId(attributes.get("id").toString())
                .nickname((String) properties.get("nickname"))
                .build();
    }
}
