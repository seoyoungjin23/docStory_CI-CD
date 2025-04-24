package com.ky.docstory.service.proposal;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.dto.proposal.ProposalCreateRequest;
import com.ky.docstory.dto.proposal.ProposalResponse;
import com.ky.docstory.dto.proposal.ProposalUpdateRequest;
import com.ky.docstory.entity.*;
import com.ky.docstory.repository.HistoryRepository;
import com.ky.docstory.repository.ProposalRepository;
import com.ky.docstory.repository.RepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProposalServiceImpl implements ProposalService {

    private final ProposalRepository proposalRepository;
    private final HistoryRepository historyRepository;
    private final RepositoryRepository repositoryRepository;

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

    @Override
    @Transactional(readOnly = true)
    public ProposalResponse getProposalById(UUID proposalId, User currentUser) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        return ProposalResponse.from(proposal);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProposalResponse> getProposalsByRepository(UUID repositoryId, User currentUser) {
        if (!repositoryRepository.existsById(repositoryId)) {
            throw new BusinessException(DocStoryResponseCode.NOT_FOUND);
        }

        return proposalRepository.findAllByHistoryRepositoryId(repositoryId).stream()
                .map(ProposalResponse::from)
                .toList();
    }

    @Override
    @Transactional
    public ProposalResponse updateProposal(UUID proposalId, ProposalUpdateRequest request, User currentUser) {
        Proposal proposal = proposalRepository.findById(proposalId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        if (!proposal.getCreatedBy().getId().equals(currentUser.getId())) {
            throw new BusinessException(DocStoryResponseCode.FORBIDDEN);
        }

        if (proposal.getStatus() == Proposal.Status.MERGED) {
            throw new BusinessException(DocStoryResponseCode.RESOURCE_CONFLICT); // 이미 반영된 제안은 수정 불가
        }

        proposal.updateContent(request.title(), request.description());

        return ProposalResponse.from(proposal);
    }

}
