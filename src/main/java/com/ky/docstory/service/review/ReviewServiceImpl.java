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
import java.util.UUID;

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

        Review review = Review.builder()
                .proposal(proposal)
                .reviewer(currentUser)
                .comment(request.comment())
                .build();

        Review savedReview = reviewRepository.save(review);
        UserResponse userResponse = getUserResponse(savedReview.getReviewer());

        return ReviewResponse.from(savedReview, userResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByProposal(UUID proposalId, User currentUser) {
        Proposal proposal = getProposalById(proposalId);

        List<Review> reviews = reviewRepository.findByProposal(proposal);
        return reviews.stream()
                .map(review -> {
                    UserResponse userResponse = getUserResponse(review.getReviewer());
                    return ReviewResponse.from(review, userResponse);
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