package com.ky.docstory.dto.repository;

import com.ky.docstory.entity.Repository;
import com.ky.docstory.entity.File;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Schema(description = "레포지토리 상세 조회 응답 DTO")
public record RepositoryDetailResponse(
        @Schema(description = "레포지토리 ID", example = "123e4567-e89b-12d3-a456-426614174000")
        UUID id,

        @Schema(description = "레포지토리 이름", example = "내 프로젝트")
        String name,

        @Schema(description = "레포지토리 설명", example = "프로젝트 설명입니다.")
        String description,

        @Schema(description = "소유자 정보")
        OwnerInfo owner,

        @Schema(description = "루트 파일 목록")
        List<FileInfo> rootFiles,

        @Schema(description = "파일 유형 목록")
        Set<String> fileTypes,

        @Schema(description = "생성일시")
        LocalDateTime createdAt,

        @Schema(description = "수정일시")
        LocalDateTime updatedAt
) {
    @Schema(description = "소유자 정보")
    public record OwnerInfo(
            @Schema(description = "소유자 ID", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID id,

            @Schema(description = "소유자 닉네임", example = "홍길동")
            String nickname,

            @Schema(description = "소유자 이메일", example = "hong@example.com")
            String email
    ) {}

    @Schema(description = "파일 정보")
    public record FileInfo(
            @Schema(description = "파일 ID", example = "123e4567-e89b-12d3-a456-426614174000")
            UUID id,

            @Schema(description = "원본 파일명", example = "README.docx")
            String originFilename,

            @Schema(description = "저장된 파일명", example = "file_123456.docx")
            String saveFilename,

            @Schema(description = "파일 경로", example = "/uploads/docs/file_123456.docx")
            String filepath,

            @Schema(description = "파일 타입", example = "DOCX")
            String fileType,

            @Schema(description = "파일 레벨 (깊이)", example = "0")
            int level,

            @Schema(description = "생성일시")
            LocalDateTime createdAt
    ) {
        public static FileInfo from(File file) {
            return new FileInfo(
                    file.getId(),
                    file.getOriginFilename(),
                    file.getSaveFilename(),
                    file.getFilepath(),
                    file.getFileType().name(),
                    file.getLevel(),
                    file.getCreatedAt()
            );
        }
    }

    public static RepositoryDetailResponse from(Repository repository, List<File> rootFiles) {
        Set<String> fileTypes = new HashSet<>();
        rootFiles.forEach(file -> fileTypes.add(file.getFileType().name()));

        return new RepositoryDetailResponse(
                repository.getId(),
                repository.getName(),
                repository.getDescription(),
                new OwnerInfo(
                        repository.getOwner().getId(),
                        repository.getOwner().getNickname(),
                        repository.getOwner().getEmail()
                ),
                rootFiles.stream()
                        .map(FileInfo::from)
                        .toList(),
                fileTypes,
                repository.getCreatedAt(),
                repository.getUpdatedAt()
        );
    }
} 