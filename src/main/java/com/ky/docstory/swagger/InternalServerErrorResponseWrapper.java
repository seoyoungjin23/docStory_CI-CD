package com.ky.docstory.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "500 서버 내부 오류 응답")
public class InternalServerErrorResponseWrapper {

    @Schema(description = "응답 코드", example = "500")
    public int code;

    @Schema(description = "응답 메시지", example = "서버 내부 오류가 발생했습니다.")
    public String message;

    @Schema(description = "응답 데이터", example = "null")
    public Object data;
}
