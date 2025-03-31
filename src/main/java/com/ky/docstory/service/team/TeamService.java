package com.ky.docstory.service.team;

import com.ky.docstory.dto.team.RoleUpdateResponse;
import com.ky.docstory.dto.team.TeamMemberResponse;
import com.ky.docstory.entity.Team;
import com.ky.docstory.entity.User;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    List<TeamMemberResponse> getTeamMembers(UUID repositoryId, User currentUser);

    RoleUpdateResponse updateTeamMemberRole(UUID repositoryId, UUID memberId, User currentUser, Team.Role newRole);

    void removeTeamMember(UUID repositoryId, UUID memberId, User currentUser);

}
