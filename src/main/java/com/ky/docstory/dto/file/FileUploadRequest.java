package com.ky.docstory.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Schema(description = "파일 업로드 요청 DTO")
public record FileUploadRequest(

        @NotNull(message = "레포지토리 ID는 필수입니다.")
        @Schema(description = "레포지토리 ID", example = "9593d6b7-8e22-43be-a5bc-c586dfed0eb3", required = true)
        UUID repositoryId,

        @Schema(description = "부모 파일 ID", example = "66f0e8c5-4f9f-4f92-ba51-749e23bfc3a9", required = false)
        UUID parentFileId
) {}
