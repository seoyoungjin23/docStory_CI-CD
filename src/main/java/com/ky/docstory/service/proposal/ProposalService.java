package com.ky.docstory.service.proposal;

import com.ky.docstory.dto.proposal.ProposalCreateRequest;
import com.ky.docstory.dto.proposal.ProposalResponse;
import com.ky.docstory.dto.proposal.ProposalStatusUpdateRequest;
import com.ky.docstory.dto.proposal.ProposalUpdateRequest;
import com.ky.docstory.entity.User;

import java.util.List;
import java.util.UUID;

public interface ProposalService {
    ProposalResponse createProposal(ProposalCreateRequest request, User currentUser);

    ProposalResponse getProposalById(UUID proposalId, User currentUser);

    List<ProposalResponse> getProposalsByRepository(UUID repositoryId, User currentUser);

    ProposalResponse updateProposal(UUID proposalId, ProposalUpdateRequest request, User currentUser);

    ProposalResponse updateProposalStatus(UUID proposalId, ProposalStatusUpdateRequest request, User currentUser);

}
