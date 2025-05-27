package com.ky.docstory.dto.user;

import com.ky.docstory.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 응답 DTO")
public record UserResponse(

        @Schema(description = "현재 사용자 소셜 로그인 고유 ID", example = "108254669825103584337")
        String providerId,

        @Schema(description = "현재 사용자 닉네임", example = "영진")
        String nickname,

        @Schema(description = "현재 사용자 프로필 이미지", example = "인코딩된 프로필 이미지")
        String profileImage,

        @Schema(description = "현재 사용자 이메일", example = "영진@example.com")
        String email
) {
    public static UserResponse from(User user, String profileImage) {
        return new UserResponse(
                user.getProviderId(),
                user.getNickname(),
                profileImage,
                user.getEmail()
        );
    }
}
