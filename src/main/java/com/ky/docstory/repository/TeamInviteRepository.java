package com.ky.docstory.repository;

import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.TeamInvite;
import com.ky.docstory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TeamInviteRepository extends JpaRepository<TeamInvite, UUID> {
    boolean existsByRepositoryAndInvitee(Repository repository, User invitee);

    Optional<TeamInvite> findByRepositoryAndInvitee(Repository repository, User invitee);
}
