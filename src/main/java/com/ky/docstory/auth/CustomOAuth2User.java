package com.ky.docstory.auth;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User oAuth2User;
    private final String providerId;
    private final String nickname;
    private final String profileImage;
    private final String email;

    public CustomOAuth2User(OAuth2User oAuth2User, String providerId, String nickname, String profileImage,
                            String email) {
        this.oAuth2User = oAuth2User;
        this.providerId = providerId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.email = email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public String getName() {
        return providerId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public String getProviderId() {
        return providerId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getEmail() {
        return email;
    }
}
