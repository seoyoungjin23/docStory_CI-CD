package com.ky.docstory.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "이미 처리된 요청에 대한 에러 응답 예시")
public class AlreadyHandledErrorResponseWrapper {

    @Schema(description = "응답 코드", example = "1016")
    public int code;

    @Schema(description = "응답 메시지", example = "이미 처리된 요청입니다.")
    public String message;

    @Schema(description = "응답 데이터", nullable = true)
    public Object data;
}
