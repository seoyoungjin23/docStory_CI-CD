package com.ky.docstory.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum DocStoryResponseCode {
    SUCCESS(100, "요청이 성공적으로 처리되었습니다.", HttpStatus.OK),
    BAD_REQUEST(400, "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401, "인증에 실패했습니다.", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403, "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    NOT_FOUND(404, "요청한 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // 비즈니스 로직 관련 상세 오류
    VALIDATION_ERROR(1000, "유효성 검사에 실패했습니다.", HttpStatus.BAD_REQUEST),
    DUPLICATE_RESOURCE(1001, "중복된 데이터가 존재합니다.", HttpStatus.CONFLICT),
    RESOURCE_CONFLICT(1002, "리소스 충돌이 발생했습니다.", HttpStatus.CONFLICT),
    DATABASE_ERROR(1003, "데이터베이스 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    REGISTRATION_FAILED(1004, "데이터 등록에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    FILE_UPLOAD_FAILED(1005, "파일 업로드에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    PARAMETER_ERROR(1006, "파라미터가 올바르지 않습니다.", HttpStatus.BAD_REQUEST);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
