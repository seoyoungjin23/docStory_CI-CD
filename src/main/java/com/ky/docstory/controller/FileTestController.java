package com.ky.docstory.controller;

import com.ky.docstory.common.code.DocStoryResponseCode;
import com.ky.docstory.common.dto.DocStoryResponseBody;
import com.ky.docstory.dto.FileDownloadResponse;
import com.ky.docstory.service.FileService;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import org.springframework.web.util.UriUtils;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileTestController {

    private final FileService fileService;

    /**
     * 파일 업로드 API
     */
    @PostMapping("/upload")
    public DocStoryResponseBody<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filePath = fileService.uploadFile(file);
            return DocStoryResponseBody.success(filePath);
        } catch (IOException e) {
            return DocStoryResponseBody.error(
                    DocStoryResponseCode.FILE_UPLOAD_FAILED.getCode(),
                    DocStoryResponseCode.FILE_UPLOAD_FAILED.getMessage() + ": " + e.getMessage()
            );
        }
    }

    /**
     * 파일 다운로드 API
     */
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam Long fileId) {
        Optional<FileDownloadResponse> fileResponse = fileService.downloadFile(fileId);

        if (fileResponse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        FileDownloadResponse response = fileResponse.get();
        String filename = response.getFilename() + "." + response.getFileType();
        String encodedFilename = UriUtils.encode(filename, StandardCharsets.UTF_8);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                .body(response.getData());
    }

}
