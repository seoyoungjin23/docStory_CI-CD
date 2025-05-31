package com.ky.docstory.swagger;

import com.ky.docstory.dto.review.ReviewResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "리뷰 응답 Wrapper")
public class ReviewResponseWrapper {

    @Schema(description = "응답 코드", example = "100")
    public int code;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    public String message;

    @Schema(description = "리뷰 정보")
    public ReviewResponse data;
} 