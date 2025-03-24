package com.ky.docstory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImage {
    private String filePath;
    private String fileType;
    private String saveFilename;
}
