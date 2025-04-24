package com.ky.docstory.service.proposal;

import com.ky.docstory.dto.proposal.ProposalCreateRequest;
import com.ky.docstory.dto.proposal.ProposalResponse;
import com.ky.docstory.entity.User;

public interface ProposalService {
    ProposalResponse createProposal(ProposalCreateRequest request, User currentUser);
}
