package com.ky.docstory.swagger;

import com.ky.docstory.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "현재 사용자 조회 응답 구조")
public class UserResponseWrapper {

    @Schema(description = "응답 코드", example = "100")
    public int code;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    public String message;

    @Schema(description = "실제 응답 데이터")
    public UserResponse data;
}