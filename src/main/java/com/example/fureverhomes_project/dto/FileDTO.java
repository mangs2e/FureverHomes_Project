package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.File;
import lombok.Getter;

@Getter
public class FileDTO {
    private String originalFileName;
    private String saveFileName;
    private Long fileSize;

    public FileDTO(String originalFileName, String saveFileName, Long fileSize) {
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
        this.fileSize = fileSize;
    }

    public File toEntity(File file) {
        return File.builder()
                .original_name(originalFileName)
                .save_name(saveFileName)
                .size(fileSize).build();
    }
}
