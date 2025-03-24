package com.ky.docstory.controller;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.auth.CustomOAuth2User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrentUserController {

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@CurrentUser CustomOAuth2User currentUser) {
        return ResponseEntity.ok("ProviderId: " + currentUser.getProviderId() +
                ", Nickname: " + currentUser.getNickname() +
                ", ProfileImage: " + currentUser.getProfileImage());
    }
}
