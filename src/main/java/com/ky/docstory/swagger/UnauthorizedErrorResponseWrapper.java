package com.ky.docstory.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "401 인증 실패 응답")
public class UnauthorizedErrorResponseWrapper {

    @Schema(description = "응답 코드", example = "401")
    public int code;

    @Schema(description = "응답 메시지", example = "인증에 실패했습니다.")
    public String message;

    @Schema(description = "응답 데이터", example = "null")
    public Object data;
}
