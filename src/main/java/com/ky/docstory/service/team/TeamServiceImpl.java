package com.ky.docstory.service.team;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.dto.team.TeamMemberResponse;
import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.Team;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.RepositoryRepository;
import com.ky.docstory.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final RepositoryRepository repositoryRepository;

    @Override
    public List<TeamMemberResponse> getTeamMembers(UUID repositoryId, User currentUser) {
        Repository repository = repositoryRepository.findById(repositoryId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        boolean isMember = teamRepository.existsByRepositoryAndUser(repository, currentUser);
        if (!isMember) {
            throw new BusinessException(DocStoryResponseCode.FORBIDDEN);
        }

        return teamRepository.findAllByRepository(repository).stream()
                .map(TeamMemberResponse::from)
                .toList();
    }
}
