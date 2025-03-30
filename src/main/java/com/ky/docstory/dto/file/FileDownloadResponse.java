package com.ky.docstory.dto.file;

import com.ky.docstory.entity.File;
import java.nio.charset.StandardCharsets;
import org.springframework.web.util.UriUtils;

public record FileDownloadResponse(
        String filename,
        File.FileType fileType,
        byte[] data
) {
    public String encodedFilename() {
        return UriUtils.encode(filename + "." + fileType.name().toLowerCase(), StandardCharsets.UTF_8);
    }
}