package com.ky.docstory.controller.proposal;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.proposal.ProposalCreateRequest;
import com.ky.docstory.dto.proposal.ProposalResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.service.proposal.ProposalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProposalController implements ProposalApi {

    private final ProposalService proposalService;

    @Override
    public ResponseEntity<DocStoryResponseBody<ProposalResponse>> createProposal(
            @CurrentUser User currentUser,
            @Valid ProposalCreateRequest request) {
        ProposalResponse response = proposalService.createProposal(request, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(response));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<ProposalResponse>> getProposalById(
            UUID proposalId, User currentUser) {
        ProposalResponse response = proposalService.getProposalById(proposalId, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(response));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<List<ProposalResponse>>> getProposalsByRepository(
            UUID repositoryId, User currentUser) {
        List<ProposalResponse> responses = proposalService.getProposalsByRepository(repositoryId, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(responses));
    }


}
