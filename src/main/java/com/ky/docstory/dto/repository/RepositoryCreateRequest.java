package com.ky.docstory.dto.repository;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "레포지토리 생성 요청 DTO")
public record RepositoryCreateRequest(

        @NotBlank(message = "레포지토리 이름은 필수입니다.")
        @Size(max = 255, message = "레포지토리 이름은 255자 이하여야 합니다.")
        @Schema(description = "레포지토리 이름", example = "docstory-project", required = true)
        String name,

        @Size(max = 2000, message = "설명은 2000자 이하여야 합니다.")
        @Schema(description = "레포지토리 설명", example = "팀 협업을 위한 문서 저장소", required = false)
        String description
) {}
