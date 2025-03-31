package com.ky.docstory.dto.teaminvite;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "팀 초대 요청 DTO")
public record TeamInviteRequest(

        @NotNull(message = "레포지토리 ID는 필수입니다.")
        @Schema(description = "레포지토리 ID", example = "7f310b23-f68d-4c4f-9910-94c96f00a4b2")
        UUID repositoryId,

        @NotNull(message = "초대 대상 사용자 ID는 필수입니다.")
        @Schema(description = "초대할 사용자 ID", example = "3e1192a9-3244-46ef-bc88-8de938974a9a")
        UUID inviteeId
) {}
