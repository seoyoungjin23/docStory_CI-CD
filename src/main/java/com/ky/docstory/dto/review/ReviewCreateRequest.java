package com.ky.docstory.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "리뷰 생성 요청 DTO")
public record ReviewCreateRequest(
    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = 2000, message = "댓글은 2000자 이하여야 합니다.")
    @Schema(description = "리뷰 내용", example = "이 부분은 다음과 같이 수정하는 것이 좋을 것 같습니다.", required = true)
    String comment
) {} 