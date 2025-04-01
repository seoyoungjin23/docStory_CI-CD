package com.ky.docstory.controller.file;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.file.FileUploadRequest;
import com.ky.docstory.dto.file.FileUploadResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.swagger.BadRequestErrorResponseWrapper;
import com.ky.docstory.swagger.FileDownloadResponseWrapper;
import com.ky.docstory.swagger.FileUploadResponseWrapper;
import com.ky.docstory.swagger.InternalServerErrorResponseWrapper;
import com.ky.docstory.swagger.NotFoundErrorResponseWrapper;
import com.ky.docstory.swagger.UnauthorizedErrorResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File", description = "File 관련 API")
@RequestMapping("/api/files")
public interface FileApi {

    @PostMapping(
            value = "/upload",
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    MediaType.APPLICATION_JSON_VALUE
            }
    )
    @Operation(summary = "파일 업로드", description = "파일 원본을 S3에 업로드하고 파일 저장 경로를 DB에 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 업로드에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = FileUploadResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = BadRequestErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "요청한 리소스를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = NotFoundErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class))),

    })
    ResponseEntity<DocStoryResponseBody<FileUploadResponse>> uploadFile(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @Parameter(
                    description = "업로드할 파일",
                    content = @Content(mediaType = "multipart/form-data")
            )
            @RequestParam MultipartFile file,
            @RequestPart @Valid FileUploadRequest fileUploadRequest
    );

    @GetMapping("/download/{fileId}")
    @Operation(summary = "파일 다운로드", description = "파일을 다운로드 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "파일 다운로드에 성공하였습니다.",
                    content = @Content(schema = @Schema(implementation = FileDownloadResponseWrapper.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.",
                    content = @Content(schema = @Schema(implementation = BadRequestErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다.",
                    content = @Content(schema = @Schema(implementation = UnauthorizedErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "404", description = "요청한 리소스를 찾을 수 없습니다.",
                    content = @Content(schema = @Schema(implementation = NotFoundErrorResponseWrapper.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류가 발생했습니다.",
                    content = @Content(schema = @Schema(implementation = InternalServerErrorResponseWrapper.class))),

    })
    ResponseEntity<byte[]> downloadFile(
            @Parameter(hidden = true) @CurrentUser User currentUser,
            @PathVariable UUID fileId
    );

}
