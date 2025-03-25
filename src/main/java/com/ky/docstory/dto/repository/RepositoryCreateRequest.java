package com.ky.docstory.dto.repository;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레포지토리 생성 요청 DTO")
public record RepositoryCreateRequest(

        @Schema(description = "레포지토리 이름", example = "docstory-project", required = true)
        String name,

        @Schema(description = "레포지토리 설명", example = "팀 협업을 위한 문서 저장소", required = false)
        String description
) {}
