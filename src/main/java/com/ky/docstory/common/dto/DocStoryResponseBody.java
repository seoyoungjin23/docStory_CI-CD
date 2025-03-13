package com.ky.docstory.common.dto;

import com.ky.docstory.common.code.DocStoryResponseCode;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class DocStoryResponseBody<T> {
    private final int code;       // 내부 비즈니스 코드 (예: 100: 성공, 102: DB 오류 등)
    private final String message; // 응답 메시지
    private final T data;         // 실제 응답 데이터

    // 성공 응답 생성 메서드
    public static <T> DocStoryResponseBody<T> success(T data) {
        return DocStoryResponseBody.<T>builder()
                .code(DocStoryResponseCode.SUCCESS.getCode())
                .message(DocStoryResponseCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    // 에러 응답 생성 메서드
    public static <T> DocStoryResponseBody<T> error(int code, String message) {
        return DocStoryResponseBody.<T>builder()
                .code(code)
                .message(message)
                .data(null)
                .build();
    }
}
