package com.ky.docstory.service.teaminvite;

import com.ky.docstory.dto.teaminvite.TeamInviteRequest;
import com.ky.docstory.dto.teaminvite.TeamInviteResponse;
import com.ky.docstory.entity.User;

import java.util.UUID;

public interface TeamInviteService {
    TeamInviteResponse inviteUserToRepository(TeamInviteRequest request, User inviter);

    TeamInviteResponse acceptInvite(UUID inviteId, User currentUser);

    TeamInviteResponse rejectInvite(UUID inviteId, User currentUser);

}
