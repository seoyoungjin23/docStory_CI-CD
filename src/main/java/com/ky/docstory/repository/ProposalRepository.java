package com.ky.docstory.repository;

import com.ky.docstory.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProposalRepository extends JpaRepository<Proposal, UUID> {
    @Query("SELECT p FROM Proposal p WHERE p.history.repository.id = :repositoryId")
    List<Proposal> findAllByHistoryRepositoryId(@Param("repositoryId") UUID repositoryId);

}
