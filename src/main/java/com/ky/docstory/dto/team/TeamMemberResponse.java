package com.ky.docstory.dto.team;

import com.ky.docstory.entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "팀원 목록 응답 DTO")
public record TeamMemberResponse(

        @Schema(description = "팀원 ID", example = "ebdbeb92-1234-44fe-b78b-0d2e613f65a3")
        UUID userId,

        @Schema(description = "팀원 닉네임", example = "kanguk")
        String nickname,

        @Schema(description = "프로필 이미지 경로", example = "https://example.com/profile.jpg")
        String profileImage,

        @Schema(description = "팀원 이메일", example = "kanguk@example.com")
        String email,

        @Schema(description = "팀원 역할", example = "CONTRIBUTOR")
        String role
) {
    public static TeamMemberResponse from(Team team) {
        return new TeamMemberResponse(
                team.getUser().getId(),
                team.getUser().getNickname(),
                team.getUser().getProfilePath(),
                team.getUser().getEmail(),
                team.getRole().name()
        );
    }
}
