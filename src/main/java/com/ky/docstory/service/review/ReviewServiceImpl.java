package com.ky.docstory.service.review;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.exception.BusinessException;
import com.ky.docstory.dto.review.ReviewCreateRequest;
import com.ky.docstory.dto.review.ReviewResponse;
import com.ky.docstory.dto.review.ReviewUpdateRequest;
import com.ky.docstory.dto.user.UserResponse;
import com.ky.docstory.entity.Proposal;
import com.ky.docstory.entity.Review;
import com.ky.docstory.entity.User;
import com.ky.docstory.repository.ProposalRepository;
import com.ky.docstory.repository.ReviewRepository;
import com.ky.docstory.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProposalRepository proposalRepository;
    private final UserService userService;

    @Override
    @Transactional
    public ReviewResponse createReview(UUID proposalId, ReviewCreateRequest request, User currentUser) {
        Proposal proposal = getProposalById(proposalId);

        if (request.parentId() != null) {
            Review parentReview = reviewRepository.findById(request.parentId())
                    .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));
            
            if (!parentReview.getProposal().getId().equals(proposalId)) {
                throw new BusinessException(DocStoryResponseCode.NOT_FOUND);
            }
        }

        Review review = Review.builder()
                .proposal(proposal)
                .reviewer(currentUser)
                .comment(request.comment())
                .parentId(request.parentId())
                .build();

        Review savedReview = reviewRepository.save(review);
        UserResponse userResponse = getUserResponse(savedReview.getReviewer());

        return ReviewResponse.from(savedReview, userResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByProposal(UUID proposalId, User currentUser) {
        Proposal proposal = getProposalById(proposalId);

        List<Review> allReviews = reviewRepository.findByProposal(proposal);
        Map<UUID, List<Review>> repliesByParentId = allReviews.stream()
                .filter(Review::isReply)
                .collect(Collectors.groupingBy(Review::getParentId));

        return allReviews.stream()
                .filter(review -> !review.isReply())
                .map(review -> {
                    UserResponse userResponse = getUserResponse(review.getReviewer());
                    List<Review> replies = repliesByParentId.getOrDefault(review.getId(), List.of());
                    return ReviewResponse.from(review, userResponse, replies);
                })
                .toList();
    }

    @Override
    @Transactional
    public ReviewResponse updateReview(UUID proposalId, UUID reviewId, ReviewUpdateRequest request, User currentUser) {
        getProposalById(proposalId);
        Review review = getReviewByIdAndValidate(reviewId, proposalId, currentUser);

        review.updateComment(request.comment());
        Review updatedReview = reviewRepository.save(review);

        UserResponse userResponse = getUserResponse(updatedReview.getReviewer());

        return ReviewResponse.from(updatedReview, userResponse);
    }

    @Override
    @Transactional
    public void deleteReview(UUID proposalId, UUID reviewId, User currentUser) {
        getProposalById(proposalId);
        Review review = getReviewByIdAndValidate(reviewId, proposalId, currentUser);

        reviewRepository.delete(review);
    }

    private Proposal getProposalById(UUID proposalId) {
        return proposalRepository.findById(proposalId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));
    }

    private UserResponse getUserResponse(User user) {
        return userService.getUserInfo(user);
    }

    private Review getReviewByIdAndValidate(UUID reviewId, UUID proposalId, User currentUser) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BusinessException(DocStoryResponseCode.NOT_FOUND));

        if (!review.getProposal().getId().equals(proposalId)) {
            throw new BusinessException(DocStoryResponseCode.NOT_FOUND);
        }

        if (!review.getReviewer().getId().equals(currentUser.getId())) {
            throw new BusinessException(DocStoryResponseCode.FORBIDDEN);
        }

        return review;
    }
} 