package com.ky.docstory.dto.user;

import com.ky.docstory.entity.TeamInvite;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

@Schema(description = "사용자가 받은 팀 초대 응답 DTO")
public record UserInvitationResponse(

        @Schema(description = "초대 ID", example = "e4ed2265-7f64-41a0-ab0b-68bba13603b4")
        UUID invitationId,

        @Schema(description = "초대자 닉네임", example = "영진")
        String inviterNickname,

        @Schema(description = "레포지토리 이름", example = "docstory-project")
        String repositoryName

) {
    public static UserInvitationResponse from(TeamInvite invite) {
        return new UserInvitationResponse(
                invite.getId(),
                invite.getInviter().getNickname(),
                invite.getRepository().getName()
        );
    }
}
