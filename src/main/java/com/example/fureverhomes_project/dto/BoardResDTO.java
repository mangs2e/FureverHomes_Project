package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Board;
import com.example.fureverhomes_project.entity.File;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResDTO {
    private Long board_id;
//    private Long member_id;
    private String title;
    private LocalDateTime uploadDate;
    private int views;
    private String writer;
    private String content;
    private boolean isEqualLogin;
    private List<FileDTO> files;

    public BoardResDTO(Board entity) {
        this.board_id = entity.getId();
//        this.member_id = entity.getMember().getId();
        this.title = entity.getTitle();
        this.uploadDate = entity.getUploadDate();
        this.views = entity.getViews();
        this.writer = entity.getMember().getName();
        this.content = entity.getContent();
    }

    public void setFiles(Board entity) {
        if (!entity.getFiles().isEmpty()) {
            this.files = entity.getFiles().stream().map(FileDTO::new).collect(Collectors.toList());
        }
    }

    public void setEqualLogin() {
        this.isEqualLogin = true;
    }
}
