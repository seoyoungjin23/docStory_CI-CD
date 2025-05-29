package com.ky.docstory.dto.user;

import com.ky.docstory.entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "레포지토리 내 유저 권한 응답 DTO")
public record UserAuthorityResponse(

        @Schema(description = "레포지토리 Id", example = "123e4567-e89b-12d3-a456-426614174000", required = true)
        UUID repositoryId,

        @Schema(description = "유저의 권한", example = "ADMIN", required = true)
        String authority

) {
    public static UserAuthorityResponse from(Team team) {
        return new UserAuthorityResponse(
                team.getRepository().getId(),
                team.getRole().name()
        );
    }
}
