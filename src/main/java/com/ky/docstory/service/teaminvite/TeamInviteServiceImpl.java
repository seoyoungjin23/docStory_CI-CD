package com.ky.docstory.service.teaminvite;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.dto.teaminvite.TeamInviteRequest;
import com.ky.docstory.dto.teaminvite.TeamInviteResponse;
import com.ky.docstory.entity.*;
import com.ky.docstory.repository.RepositoryRepository;
import com.ky.docstory.repository.TeamInviteRepository;
import com.ky.docstory.repository.UserRepository;
import com.ky.docstory.service.teaminvite.TeamInviteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamInviteServiceImpl implements TeamInviteService {

    private final RepositoryRepository repositoryRepository;
    private final TeamInviteRepository teamInviteRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TeamInviteResponse inviteUserToRepository(TeamInviteRequest request, User inviter) {
        Repository repository = repositoryRepository.findById(request.repositoryId())
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        if (!repository.getOwner().getId().equals(inviter.getId())) {
            throw new BusinessException(DocStoryResponseCode.FORBIDDEN);
        }

        if (inviter.getId().equals(request.inviteeId())) {
            throw new BusinessException(DocStoryResponseCode.PARAMETER_ERROR);
        }

        User invitee = userRepository.findById(request.inviteeId())
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
}
