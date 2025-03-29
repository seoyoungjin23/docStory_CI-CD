package com.ky.docstory.service.team;

import com.ky.docstory.dto.team.TeamMemberResponse;
import com.ky.docstory.entity.User;

import java.util.List;
import java.util.UUID;

public interface TeamService {
    List<TeamMemberResponse> getTeamMembers(UUID repositoryId, User currentUser);
}
