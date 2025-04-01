package com.ky.docstory.dto.file;

import com.ky.docstory.entity.File;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import java.util.UUID;

public record FileUploadResponse(

        @Schema(description = "파일 ID", example = "9593d6b7-8e22-43be-a5bc-c586dfed0eb3")
        UUID id,

        @Schema(description = "파일명", example = "docstory_manual_수정.docx")
        String name,

        @Schema(description = "파일 확장자", example = "DOCX")
        File.FileType fileType,

        @Schema(description = "저장소명", example = "docstory-project")
        String repositoryName,

        @Schema(description = "부모 파일명", example = "docstory_manual.docx")
        String parentFileName
) {
    public static FileUploadResponse from(File file){
        return new FileUploadResponse(
                file.getId(),
                file.getOriginFilename(),
                file.getFileType(),
                file.getRepository().getName(),
                Optional.ofNullable(file.getParentFile())
                        .map(File::getOriginFilename)
                        .orElse(null)
        );
    }
}
