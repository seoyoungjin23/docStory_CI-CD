package com.ky.docstory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadResponse {
    private String filename;
    private String fileType;
    private byte[] data;
}