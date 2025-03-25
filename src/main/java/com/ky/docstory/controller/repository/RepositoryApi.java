package com.ky.docstory.controller.repository;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.repository.RepositoryCreateRequest;
import com.ky.docstory.dto.repository.RepositoryResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.swagger.BadRequestErrorResponseWrapper;
import com.ky.docstory.swagger.InternalServerErrorResponseWrapper;
import com.ky.docstory.swagger.RepositoryCreateResponseWrapper;
import com.ky.docstory.swagger.UnauthorizedErrorResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Repository", description = "레포지토리 관련 API")
@RequestMapping("/api/repositories")
public interface RepositoryApi {

    @PostMapping
    @Operation(summary = "레포지토리 생성", description = "새로운 레포지토리를 생성하고, 생성자는 팀장(ADMIN)으로 등록됩니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "레포지토리가 성공적으로 생성되었습니다.",
                    content = @Content(schema = @Schema(implementation = RepositoryCreateResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = BadRequestErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class))),

    })
    ResponseEntity<DocStoryResponseBody<RepositoryResponse>> createRepository(
            @Parameter(hidden = true)
            @CurrentUser User currentUser,
            @RequestBody @Valid RepositoryCreateRequest request);
}
