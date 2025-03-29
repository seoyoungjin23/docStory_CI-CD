package com.ky.docstory.dto.team;

import com.ky.docstory.entity.Team;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "팀원 권한 변경 요청 DTO")
public record RoleUpdateRequest(
        @Schema(description = "변경할 권한", example = "REVIEWER", required = true)
        Team.Role role
) {}
