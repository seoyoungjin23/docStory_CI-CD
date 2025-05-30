package com.ky.docstory.controller.review;

import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.review.ReviewCreateRequest;
import com.ky.docstory.dto.review.ReviewResponse;
import com.ky.docstory.dto.review.ReviewUpdateRequest;
import com.ky.docstory.entity.User;
import com.ky.docstory.service.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ReviewController implements ReviewApi {

    private final ReviewService reviewService;

    @Override
    public ResponseEntity<DocStoryResponseBody<ReviewResponse>> createReview(
            User currentUser,
            UUID proposalId,
            @Valid ReviewCreateRequest request) {
        ReviewResponse response = reviewService.createReview(proposalId, request, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(response));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<List<ReviewResponse>>> getReviewsByProposal(
            User currentUser,
            UUID proposalId) {
        List<ReviewResponse> responses = reviewService.getReviewsByProposal(proposalId, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(responses));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<ReviewResponse>> updateReview(
            User currentUser,
            UUID proposalId,
            UUID reviewId,
            @Valid ReviewUpdateRequest request) {
        ReviewResponse response = reviewService.updateReview(proposalId, reviewId, request, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(response));
    }

    @Override
    public ResponseEntity<DocStoryResponseBody<Void>> deleteReview(
            User currentUser,
            UUID proposalId,
            UUID reviewId) {
        reviewService.deleteReview(proposalId, reviewId, currentUser);
        return ResponseEntity.ok(DocStoryResponseBody.success(null));
    }
} 