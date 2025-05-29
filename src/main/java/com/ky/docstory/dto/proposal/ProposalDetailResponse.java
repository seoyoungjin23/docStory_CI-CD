package com.ky.docstory.dto.proposal;

import com.ky.docstory.dto.file.FileResponse;
import com.ky.docstory.dto.user.UserResponse;
import com.ky.docstory.entity.Proposal;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Proposal 응답 DTO")
public record ProposalDetailResponse(

        @Schema(description = "Proposal ID", example = "d947891e-3c5e-4ae0-9d71-eee6f3f2c3a1")
        UUID id,

        @Schema(description = "제목", example = "문서 수정 제안")
        String title,

        @Schema(description = "설명", example = "문서 내용에 오타가 있어 수정 제안합니다.")
        String description,

        @Schema(description = "PP 생성 일자")
        LocalDateTime createdAt,

        @Schema(description = "PP 생성자 정보")
        UserResponse createdBy,

        @Schema(description = "PP 요청 파일 정보")
        FileResponse file

) {
    public static ProposalDetailResponse from(Proposal proposal, UserResponse userResponse, FileResponse fileResponse) {
        return new ProposalDetailResponse(
                proposal.getId(),
                proposal.getTitle(),
                proposal.getDescription(),
                proposal.getCreatedAt(),
                userResponse,
                fileResponse
        );
    }
}
