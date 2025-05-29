package com.ky.docstory.dto.history;

import com.ky.docstory.dto.user.UserResponse;
import com.ky.docstory.entity.History;
import com.ky.docstory.entity.History.HistoryStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "히스토리 목록 응답 DTO")
public record HistoryListResponse(

        @Schema(description = "히스토리 ID", example = "3c1a0ab4-0b90-4db4-b8e0-1ef2b6db1944")
        UUID id,

        @Schema(description = "히스토리 제목", example = "피드백 반영 파일")
        String title,

        @Schema(description = "히스토리 설명", example = "피드백 반영한 파일을 등록합니다!")
        String content,

        @Schema(description = "히스토리 파일 레벨", example = "0")
        int fileLevel,

        @Schema(description = "히스토리 생성 유저", example = "영진")
        UserResponse createdBy,

        @Schema(description = "히스토리 생성 일자", example = "2025-05-28")
        LocalDateTime createdAt,

        @Schema(description = "파일 ID", example = "2g3h5ab4-0b90-4db4-b8e0-1ef2b6db1944")
        UUID fileId,

        @Schema(description = "부모 파일 ID", example = "2g3h5ab4-0b90-4db4-b8e0-1ef2b6db1944")
        UUID parentFileId,

        @Schema(description = "히스토리 현재 상태", example = "MAIN")
        HistoryStatus historyStatus

) {
        public static HistoryListResponse from(History history, UserResponse userResponse) {
                return new HistoryListResponse(
                        history.getId(),
                        history.getTitle(),
                        history.getContent(),
                        history.getFile().getLevel(),
                        userResponse,
                        history.getCreatedAt(),
                        history.getFile().getId(),
                        history.getFile().getParentFile() != null ?
                                history.getFile().getParentFile().getId() : null,
                        history.getHistoryStatus()
                );
        }
}
