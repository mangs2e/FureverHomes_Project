package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.File;
import lombok.Getter;

@Getter
public class FileDTO {
    private String originalFileName;
    private String saveFileName;
    private String filePath;
    private Boolean isDelete;
    private Long fileSize;

    public FileDTO(String originalFileName, String saveFileName, String filePath, Long fileSize) {
        this.originalFileName = originalFileName;
        this.saveFileName = saveFileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
    }

    public FileDTO(File file) {
        this.originalFileName = file.getOriginal_name();
        this.saveFileName = file.getSave_name();
        this.filePath = file.getFile_path();
        this.isDelete = file.getIsDelete();
    }

    public File toEntity(File file) {
        return File.builder()
                .original_name(originalFileName)
                .save_name(saveFileName)
                .file_path(filePath)
                .size(fileSize).build();
    }
}
