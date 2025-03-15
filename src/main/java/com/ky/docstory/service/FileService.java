package com.ky.docstory.service;

import com.ky.docstory.dto.FileDownloadResponse;
import com.ky.docstory.entity.File;
import com.ky.docstory.repository.FileRepository;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileService {

    private final S3Template s3Template;
    private final FileRepository fileRepository;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * S3에 파일 업로드 후 DB에 저장
     */
    public String uploadFile(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        int idx = originalFilename.lastIndexOf(".");

        String filename = originalFilename.substring(0, idx);
        String fileType = originalFilename.substring(idx+1);

        String dateDir = LocalDate.now().format(DateTimeFormatter.ofPattern("yy-MM-dd"));
        String saveFilename = String.valueOf(UUID.randomUUID());
        String filePath = "files/" + dateDir + "/" + saveFilename + "." + fileType;

        try (InputStream inputStream = file.getInputStream()) {
            s3Template.upload(bucketName, filePath, inputStream);
        }

        File fileEntity = File.builder()
                .originFilename(filename)
                .saveFilename(saveFilename)
                .filepath(filePath)
                .fileType(fileType)
                .build();

        fileRepository.save(fileEntity);

        return filePath;
    }

    /**
     * S3에서 파일 다운로드
     */
    public Optional<FileDownloadResponse> downloadFile(Long fileId) {
        Optional<File> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isEmpty()) {
            return Optional.empty();
        }

        File file = optionalFile.get();
        String filePath = file.getFilepath();

        try {
            Resource resource = s3Template.download(bucketName, filePath);

            try (InputStream inputStream = resource.getInputStream()) {
                byte[] fileData = inputStream.readAllBytes();
                return Optional.of(new FileDownloadResponse(file.getOriginFilename(), file.getFileType(), fileData));
            }
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
