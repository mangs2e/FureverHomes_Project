package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardResDTO {
    private Long id;
    private String title;
    private LocalDateTime uploadDate;
    private int views;
    private String writer;
    private String content;

    public BoardResDTO(Board entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.uploadDate = entity.getUploadDate();
        this.views = entity.getViews();
        this.writer = entity.getMember().getName();
        this.content = entity.getContent();
    }
}
