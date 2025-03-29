package com.ky.docstory.controller.team;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
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
}
