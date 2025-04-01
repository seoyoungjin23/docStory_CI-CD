package com.ky.docstory.controller.file;

import com.ky.docstory.auth.CurrentUser;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.file.FileDownloadResponse;
import com.ky.docstory.dto.file.FileUploadRequest;
import com.ky.docstory.dto.file.FileUploadResponse;
import com.ky.docstory.entity.User;
import com.ky.docstory.service.file.FileService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class FileController implements FileApi {

    private final FileService fileService;

    @Override
    public ResponseEntity<DocStoryResponseBody<FileUploadResponse>> uploadFile(
            @CurrentUser User currentUser,
            @RequestPart MultipartFile file,
            @RequestPart FileUploadRequest fileUploadRequest) {
        FileUploadResponse fileUploadResponse = fileService.uploadFile(file, fileUploadRequest.repositoryId(), fileUploadRequest.parentFileId(), currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(DocStoryResponseBody.success(fileUploadResponse));
    }

    @Override
    public ResponseEntity<byte[]> downloadFile(
            @CurrentUser User currentUser,
            @PathVariable UUID fileId) {

        FileDownloadResponse fileDownloadResponse = fileService.downloadFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileDownloadResponse.encodedFilename() + "\"")
                .body(fileDownloadResponse.data());
    }
}
