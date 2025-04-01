package com.ky.docstory.swagger;

import com.ky.docstory.dto.user.UserInvitationResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "현재 사용자 팀 초대 목록 응답 구조")
public class UserInvitationResponseWrapper {

    @Schema(description = "응답 코드", example = "100")
    public int code;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    public String message;

    @Schema(description = "실제 응답 데이터")
    public UserInvitationResponse data;
}