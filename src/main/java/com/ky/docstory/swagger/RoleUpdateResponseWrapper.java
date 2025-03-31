package com.ky.docstory.swagger;

import com.ky.docstory.dto.team.RoleUpdateResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "팀원 권한 변경 응답 Wrapper")
public class RoleUpdateResponseWrapper {

    @Schema(description = "응답 코드", example = "100")
    public int code;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    public String message;

    @Schema(description = "변경된 사용자 정보")
    public RoleUpdateResponse data;
}
