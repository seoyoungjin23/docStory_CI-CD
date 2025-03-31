package com.ky.docstory.controller.team;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.team.RoleUpdateRequest;
import com.ky.docstory.dto.team.RoleUpdateResponse;
import com.ky.docstory.dto.team.TeamMemberResponse;
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

import java.util.List;
import java.util.UUID;

@Tag(name = "Team", description = "팀 관련 API")
@RequestMapping("/api/repositories")
public interface TeamApi {

    @GetMapping("/{repositoryId}/team-members")
    @Operation(summary = "팀원 목록 조회", description = "해당 레포지토리에 속한 팀원 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀원 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = TeamMemberListResponseWrapper.class))),
            @ApiResponse(responseCode = "403", description = "팀원이 아닌 사용자는 조회할 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = ForbiddenErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 레포지토리입니다.",
                    content = @Content(schema = @Schema(implementation = NotFoundErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class)))
    })
    ResponseEntity<DocStoryResponseBody<List<TeamMemberResponse>>> getTeamMembers(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @PathVariable UUID repositoryId
    );

    @PutMapping("/{repositoryId}/team-members/{memberId}/role")
    @Operation(summary = "팀원 권한 변경", description = "ADMIN이 팀원의 권한을 REVIEWER 또는 CONTRIBUTOR로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "권한 변경 성공",
                    content = @Content(schema = @Schema(implementation = RoleUpdateResponseWrapper.class))),
            @ApiResponse(responseCode = "403", description = "접근 권한 없음 (ADMIN만 가능 또는 자기 자신 권한 변경 시)",
                    content = @Content(schema = @Schema(implementation = ForbiddenErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "레포지토리 또는 팀원을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = NotFoundErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class)))
    })
    ResponseEntity<DocStoryResponseBody<RoleUpdateResponse>> updateTeamMemberRole(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @PathVariable UUID repositoryId,
            @PathVariable UUID memberId,
            @RequestBody @Valid RoleUpdateRequest request
    );

    @Operation(summary = "팀원 추방", description = "ADMIN이 팀원 한 명을 팀에서 추방합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팀원 추방 성공",
                    content = @Content(schema = @Schema(implementation = SuccessEmptyResponseWrapper.class))),
    @ApiResponse(responseCode = "403", description = "접근 권한 없음 (ADMIN만 가능 또는 자기 자신 추방 불가)",
                    content = @Content(schema = @Schema(implementation = ForbiddenErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "레포지토리 또는 팀원을 찾을 수 없음",
                    content = @Content(schema = @Schema(implementation = NotFoundErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class)))
    })
    @DeleteMapping("/{repositoryId}/team-members/{memberId}")
    ResponseEntity<DocStoryResponseBody<Void>> removeTeamMember(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @PathVariable UUID repositoryId,
            @PathVariable UUID memberId
    );

}
