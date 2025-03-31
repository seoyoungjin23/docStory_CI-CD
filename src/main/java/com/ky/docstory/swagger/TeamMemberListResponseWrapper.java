package com.ky.docstory.swagger;

import com.ky.docstory.dto.team.TeamMemberResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "팀원 목록 조회 응답 Wrapper")
public class TeamMemberListResponseWrapper {

    @Schema(description = "응답 코드", example = "100")
    public int code;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    public String message;

    @Schema(description = "팀원 목록")
    public List<TeamMemberResponse> data;
}
