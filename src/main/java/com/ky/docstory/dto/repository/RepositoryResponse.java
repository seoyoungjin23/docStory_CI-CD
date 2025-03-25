package com.ky.docstory.dto.repository;

import com.ky.docstory.entity.Repository;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "레포지토리 응답 DTO")
public record RepositoryResponse(

        @Schema(description = "레포지토리 ID", example = "9593d6b7-8e22-43be-a5bc-c586dfed0eb3")
        UUID id,

        @Schema(description = "레포지토리 이름", example = "docstory-project")
        String name,

        @Schema(description = "레포지토리 설명", example = "팀 협업을 위한 문서 저장소")
        String description,

        @Schema(description = "레포지토리 소유자 닉네임", example = "강욱")
        String ownerNickname
) {
    public static RepositoryResponse from(Repository repository) {
        return new RepositoryResponse(
                repository.getId(),
                repository.getName(),
                repository.getDescription(),
                repository.getOwner().getNickname()
        );
    }
}
