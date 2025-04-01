package com.ky.docstory.dto.file;

import java.util.Map;
import lombok.Getter;

@Getter
public enum FileCategory {
    IMAGE(Map.of(
            "PNG", "image/png",
            "JPG", "image/jpeg",
            "JPEG", "image/jpeg"
    )),

    DOCUMENT(Map.of(
            "PDF", "application/pdf",
            "DOCX", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "HWP", "application/x-hwp",
            "HWPX", "application/vnd.hancom.hwp+xml"
    ));

    private final Map<String, String> allowedTypes;

    FileCategory(Map<String, String> allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

}