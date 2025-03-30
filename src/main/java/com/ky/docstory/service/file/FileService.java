package com.ky.docstory.service.file;

import com.ky.docstory.dto.file.FileDownloadResponse;
import com.ky.docstory.dto.file.FileUploadResponse;
import com.ky.docstory.entity.User;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileUploadResponse uploadFile(MultipartFile file, UUID repositoryId, UUID parentFileId, User currentUser);
    FileDownloadResponse downloadFile(UUID fileId);
}
