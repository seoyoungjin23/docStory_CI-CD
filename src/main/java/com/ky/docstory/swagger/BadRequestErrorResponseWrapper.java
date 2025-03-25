package com.ky.docstory.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "400 잘못된 요청 응답")
public class BadRequestErrorResponseWrapper {

    @Schema(description = "응답 코드", example = "400")
    public int code;

    @Schema(description = "응답 메시지", example = "잘못된 요청입니다.")
    public String message;

    @Schema(description = "응답 데이터", example = "null")
    public Object data;
}
