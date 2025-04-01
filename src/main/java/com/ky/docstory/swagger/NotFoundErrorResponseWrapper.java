package com.ky.docstory.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "404 리소스 없음 응답")
public class NotFoundErrorResponseWrapper {

    @Schema(description = "응답 코드", example = "404")
    public int code;

    @Schema(description = "응답 메시지", example = "요청한 리소스를 찾을 수 없습니다.")
    public String message;

    @Schema(description = "응답 데이터", example = "null")
    public Object data;
}
