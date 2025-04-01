package com.ky.docstory.dto.file;

import com.ky.docstory.entity.File;

public record FileInfo(
        String originalFilename,
        String saveFilename,
        File.FileType fileType,
        String filePath
) {}