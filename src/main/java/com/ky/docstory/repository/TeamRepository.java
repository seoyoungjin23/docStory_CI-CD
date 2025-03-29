package com.ky.docstory.repository;

import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.Team;
import com.ky.docstory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {
    boolean existsByRepositoryAndUser(Repository repository, User user);

    List<Team> findAllByRepository(Repository repository);

    Optional<Team> findByRepositoryAndUser(Repository repository, User user);

    Optional<Team> findByRepositoryAndUserId(Repository repository, UUID userId);

}
