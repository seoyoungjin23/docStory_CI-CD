package com.ky.docstory.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "403 권한 없음 응답")
public class ForbiddenErrorResponseWrapper {

    @Schema(description = "응답 코드", example = "403")
    public int code;

    @Schema(description = "응답 메시지", example = "접근 권한이 없습니다.")
    public String message;

    @Schema(description = "응답 데이터", example = "null")
    public Object data;
}
