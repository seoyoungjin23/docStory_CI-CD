package com.ky.docstory.service.review;

import com.ky.docstory.dto.review.ReviewCreateRequest;
import com.ky.docstory.dto.review.ReviewResponse;
import com.ky.docstory.dto.review.ReviewUpdateRequest;
import com.ky.docstory.entity.User;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    ReviewResponse createReview(UUID proposalId, ReviewCreateRequest request, User currentUser);
    
    List<ReviewResponse> getReviewsByProposal(UUID proposalId, User currentUser);
    
    ReviewResponse updateReview(UUID proposalId, UUID reviewId, ReviewUpdateRequest request, User currentUser);
    
    void deleteReview(UUID proposalId, UUID reviewId, User currentUser);
} 