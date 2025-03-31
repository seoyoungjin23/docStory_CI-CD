package com.ky.docstory.dto.team;

import com.ky.docstory.entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "팀원 권한 변경 응답 DTO")
public record RoleUpdateResponse(
        @Schema(description = "사용자 ID") UUID userId,
        @Schema(description = "닉네임") String nickname,
        @Schema(description = "변경된 역할") Team.Role role
) {
    public static RoleUpdateResponse from(Team team) {
        return new RoleUpdateResponse(
                team.getUser().getId(),
                team.getUser().getNickname(),
                team.getRole()
        );
    }
}
