package com.ky.docstory.dto.file;

import java.nio.charset.StandardCharsets;
import org.springframework.web.util.UriUtils;

public record FileDownloadResponse(
        String filename,
        byte[] data
) {
    public String encodedFilename() {
        return UriUtils.encode(filename, StandardCharsets.UTF_8);
    }
}