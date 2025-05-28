package com.ky.docstory.dto.repository;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "레포지토리 수정 요청 DTO")
public record RepositoryUpdateRequest(
        @NotBlank(message = "레포지토리 이름은 필수입니다.")
        @Size(min = 1, max = 255, message = "레포지토리 이름은 1자 이상 255자 이하여야 합니다.")
        @Schema(description = "레포지토리 이름", example = "수정된 프로젝트", required = true)
        String name,

        @Size(max = 1000, message = "설명은 1000자 이하여야 합니다.")
        @Schema(description = "레포지토리 설명", example = "수정된 프로젝트 설명입니다.")
        String description
) {
} 