package com.ky.docstory.controller.team;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.team.RoleUpdateRequest;
import com.ky.docstory.dto.team.RoleUpdateResponse;
import com.ky.docstory.dto.team.TeamMemberResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.service.team.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TeamController implements TeamApi {

    private final TeamService teamService;

    @Override
    public ResponseEntity<DocStoryResponseBody<List<TeamMemberResponse>>> getTeamMembers(User currentUser, UUID repositoryId) {
        List<TeamMemberResponse> response = teamService.getTeamMembers(repositoryId, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(response));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<RoleUpdateResponse>> updateTeamMemberRole(
            User currentUser, UUID repositoryId, UUID memberId, RoleUpdateRequest request) {
        RoleUpdateResponse response = teamService.updateTeamMemberRole(repositoryId, memberId, currentUser, request.role());
        return ResponseEntity.ok(DocStoryResponseBody.success(response));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<Void>> removeTeamMember(User currentUser, UUID repositoryId, UUID memberId) {
        teamService.removeTeamMember(repositoryId, memberId, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(null));
    }

}
