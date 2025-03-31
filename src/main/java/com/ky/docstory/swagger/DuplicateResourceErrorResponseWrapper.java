package com.ky.docstory.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "409 요청 충돌 응답")
public class DuplicateResourceErrorResponseWrapper {

    @Schema(description = "응답 코드", example = "409")
    public int code;

    @Schema(description = "응답 메시지", example = "요청이 서버와 충돌하였습니다.")
    public String message;

    @Schema(description = "응답 데이터", example = "null")
    public Object data;
}
