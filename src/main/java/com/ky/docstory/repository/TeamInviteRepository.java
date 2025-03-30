package com.ky.docstory.repository;

import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.TeamInvite;
import com.ky.docstory.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamInviteRepository extends JpaRepository<TeamInvite, UUID> {
    boolean existsByRepositoryAndInvitee(Repository repository, User invitee);

    Optional<TeamInvite> findByRepositoryAndInvitee(Repository repository, User invitee);

    List<TeamInvite> findAllByInviteeAndStatus(User invitee, TeamInvite.Status status);
}