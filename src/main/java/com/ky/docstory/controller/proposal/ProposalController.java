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
}
