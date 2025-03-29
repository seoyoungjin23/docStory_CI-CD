package com.ky.docstory.controller.team;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
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
}
