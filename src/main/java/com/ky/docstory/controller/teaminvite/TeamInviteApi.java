package com.ky.docstory.controller.teaminvite;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.teaminvite.TeamInviteRequest;
import com.ky.docstory.dto.teaminvite.TeamInviteResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.swagger.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "TeamInvite", description = "팀 초대 관련 API")
@RequestMapping("/api/team-invites")
public interface TeamInviteApi {

    @PostMapping
    @Operation(summary = "레포지토리 팀원 초대", description = "레포지토리의 팀원으로 사용자를 초대합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "초대가 성공적으로 완료되었습니다.",
                    content = @Content(schema = @Schema(implementation = TeamInviteResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = BadRequestErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "403", description = "레포지토리 권한이 없습니다.",
                    content = @Content(schema = @Schema(implementation = ForbiddenErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "사용자 또는 레포지토리를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = NotFoundErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "409", description = "이미 초대한 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = DuplicateResourceErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class)))
    })
    ResponseEntity<DocStoryResponseBody<TeamInviteResponse>> inviteUser(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @RequestBody @Valid TeamInviteRequest request);

    @Operation(summary = "초대 수락", description = "사용자가 받은 초대를 수락합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "초대 수락 완료",
                    content = @Content(schema = @Schema(implementation = TeamInviteResponseWrapper.class))),
            @ApiResponse(responseCode = "403", description = "초대받은 사용자가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ForbiddenErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "초대를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = NotFoundErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "409", description = "이미 수락/거절된 초대입니다.",
                    content = @Content(schema = @Schema(implementation = AlreadyHandledErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class)))
    })
    @PostMapping("/{inviteId}/accept")
    ResponseEntity<DocStoryResponseBody<TeamInviteResponse>> acceptInvite(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @PathVariable UUID inviteId);

    @Operation(summary = "초대 거절", description = "사용자가 받은 초대를 거절합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "초대 거절 완료",
                    content = @Content(schema = @Schema(implementation = TeamInviteResponseWrapper.class))),
            @ApiResponse(responseCode = "403", description = "초대받은 사용자가 아닙니다.",
                    content = @Content(schema = @Schema(implementation = ForbiddenErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "초대를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = NotFoundErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "409", description = "이미 수락/거절된 초대입니다.",
                    content = @Content(schema = @Schema(implementation = AlreadyHandledErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class)))
    })
    @PostMapping("/{inviteId}/reject")
    ResponseEntity<DocStoryResponseBody<TeamInviteResponse>> rejectInvite(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @PathVariable UUID inviteId);

}
