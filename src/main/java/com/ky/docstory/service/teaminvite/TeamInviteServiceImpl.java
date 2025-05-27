package com.ky.docstory.service.teaminvite;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.dto.teaminvite.TeamInviteRequest;
import com.ky.docstory.dto.teaminvite.TeamInviteResponse;
import com.ky.docstory.entity.*;
import com.ky.docstory.repository.RepositoryRepository;
import com.ky.docstory.repository.TeamInviteRepository;
import com.ky.docstory.repository.TeamRepository;
import com.ky.docstory.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamInviteServiceImpl implements TeamInviteService {

    private final RepositoryRepository repositoryRepository;
    private final TeamInviteRepository teamInviteRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    @Transactional
    public TeamInviteResponse inviteUserToRepository(TeamInviteRequest request, User inviter) {
        Repository repository = repositoryRepository.findById(request.repositoryId())
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        if (!repository.getOwner().getId().equals(inviter.getId())) {
            throw new BusinessException(DocStoryResponseCode.FORBIDDEN);
        }

        if (inviter.getEmail().equals(request.email())) {
            throw new BusinessException(DocStoryResponseCode.PARAMETER_ERROR);
        }

        User invitee = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        if (teamInviteRepository.existsByRepositoryAndInvitee(repository, invitee)) {
            throw new BusinessException(DocStoryResponseCode.DUPLICATE_RESOURCE);
        }

        TeamInvite invite = TeamInvite.builder()
                .repository(repository)
                .inviter(inviter)
                .invitee(invitee)
                .status(TeamInvite.Status.PENDING)
                .build();

        teamInviteRepository.save(invite);

        return TeamInviteResponse.from(invite);
    }

    @Override
    @Transactional
    public TeamInviteResponse acceptInvite(UUID inviteId, User currentUser) {
        TeamInvite invite = teamInviteRepository.findById(inviteId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        if (!invite.getInvitee().getId().equals(currentUser.getId())) {
            throw new BusinessException(DocStoryResponseCode.FORBIDDEN);
        }

        invite.accept();

        Team team = Team.builder()
                .repository(invite.getRepository())
                .user(currentUser)
                .role(Team.Role.CONTRIBUTOR)
                .build();

        teamRepository.save(team);

        return TeamInviteResponse.from(invite);
    }

    @Override
    @Transactional
    public TeamInviteResponse rejectInvite(UUID inviteId, User currentUser) {
        TeamInvite invite = teamInviteRepository.findById(inviteId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        if (!invite.getInvitee().getId().equals(currentUser.getId())) {
            throw new BusinessException(DocStoryResponseCode.FORBIDDEN);
        }

        invite.reject();

        return TeamInviteResponse.from(invite);
    }

}
