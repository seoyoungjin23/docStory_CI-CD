package com.ky.docstory.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그아웃 응답 구조")
public class UserLogoutResponseWrapper {

    @Schema(description = "응답 코드", example = "100")
    public int code;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    public String message;

    @Schema(description = "응답 데이터", example = "null")
    public Object data;
}