package com.ky.docstory.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "성공했지만 반환 데이터가 없는 응답 Wrapper")
public class SuccessEmptyResponseWrapper {

    @Schema(description = "응답 코드", example = "100")
    public int code;

    @Schema(description = "응답 메시지", example = "요청이 성공적으로 처리되었습니다.")
    public String message;

    @Schema(description = "응답 데이터 (없음)", nullable = true)
    public Object data;
}
