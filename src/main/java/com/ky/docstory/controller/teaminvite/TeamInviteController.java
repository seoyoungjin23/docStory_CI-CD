package com.ky.docstory.controller.teaminvite;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.teaminvite.TeamInviteRequest;
import com.ky.docstory.dto.teaminvite.TeamInviteResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.service.teaminvite.TeamInviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamInviteController implements TeamInviteApi {

    private final TeamInviteService teamInviteService;

    @Override
    public ResponseEntity<DocStoryResponseBody<TeamInviteResponse>> inviteUser(
            User currentUser,
            TeamInviteRequest request) {
        TeamInviteResponse response = teamInviteService.inviteUserToRepository(request, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(response));
    }
}
