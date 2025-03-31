package com.ky.docstory.dto.teaminvite;

import com.ky.docstory.entity.TeamInvite;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "팀 초대 응답 DTO")
public record TeamInviteResponse(

        @Schema(description = "초대 ID", example = "1e7b38f0-33aa-4b3e-9122-1fcae4032a65")
        UUID inviteId,

        @Schema(description = "초대 상태", example = "PENDING")
        String status
) {
    public static TeamInviteResponse from(TeamInvite invite) {
        return new TeamInviteResponse(invite.getId(), invite.getStatus().name());
    }
}
