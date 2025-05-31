package com.ky.docstory.dto.review;

import com.ky.docstory.dto.user.UserResponse;
import com.ky.docstory.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Schema(description = "리뷰 응답 DTO")
public record ReviewResponse(
    @Schema(description = "리뷰 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID id,

    @Schema(description = "제안 ID", example = "123e4567-e89b-12d3-a456-426614174000")
    UUID proposalId,

    @Schema(description = "리뷰어 정보")
    UserResponse reviewer,

    @Schema(description = "리뷰 내용", example = "이 부분은 다음과 같이 수정하는 것이 좋을 것 같습니다.")
    String comment,

    @Schema(description = "생성일시")
    LocalDateTime createdAt,

    @Schema(description = "수정일시")
    LocalDateTime updatedAt,

    @Schema(description = "부모 댓글 ID (대댓글인 경우)")
    UUID parentId,

    @Schema(description = "대댓글 목록")
    List<ReviewResponse> replies
) {
    public static ReviewResponse from(Review review, UserResponse reviewer) {
        return new ReviewResponse(
            review.getId(),
            review.getProposal().getId(),
            reviewer,
            review.getComment(),
            review.getCreatedAt(),
            review.getUpdatedAt(),
            review.getParentId(),
            List.of()
        );
    }

    public static ReviewResponse from(Review review, UserResponse reviewer, List<Review> replies) {
        return new ReviewResponse(
            review.getId(),
            review.getProposal().getId(),
            reviewer,
            review.getComment(),
            review.getCreatedAt(),
            review.getUpdatedAt(),
            review.getParentId(),
            replies.stream()
                .map(reply -> from(reply, reviewer))
                .toList()
        );
    }
} 