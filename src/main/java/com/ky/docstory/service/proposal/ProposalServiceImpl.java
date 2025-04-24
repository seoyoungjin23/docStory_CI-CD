package com.ky.docstory.service.proposal;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.dto.proposal.ProposalCreateRequest;
import com.ky.docstory.dto.proposal.ProposalResponse;
import com.ky.docstory.entity.*;
import com.ky.docstory.repository.HistoryRepository;
import com.ky.docstory.repository.ProposalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;
    private final HistoryRepository historyRepository;

    @Override
    @Transactional
    public ProposalResponse createProposal(ProposalCreateRequest request, User currentUser) {
        History history = historyRepository.findById(request.historyId())
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        Proposal proposal = Proposal.builder()
                .history(history)
                .title(request.title())
                .description(request.description())
                .status(Proposal.Status.OPEN)
                .createdBy(currentUser)
                .build();

        proposalRepository.save(proposal);
        return ProposalResponse.from(proposal);
    }
}
