package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Board;
import com.example.fureverhomes_project.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResDTO {
    private Long board_id;
    private String title;
    private LocalDateTime uploadDate;
    private int views;
    private String writer;
    private String content;
    private boolean isEqualLogin;
    private List<FileDTO> files;
    private List<CommentResDTO> comments;

    public BoardResDTO(Board entity) {
        this.board_id = entity.getId();
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

    public void setComments(Board entity) {
        if (!entity.getComments().isEmpty()) {
            this.comments = entity.getComments().stream().map(CommentResDTO::new).collect(Collectors.toList());
        }
        for (Comment comment : entity.getComments()) {
            CommentResDTO commentResDTO = new CommentResDTO(comment);
            if (commentResDTO.getMemberId().equals(entity.getMember().getId())) {
                commentResDTO.setEqualLogin();
            }
        }
        if (!entity.getComments().isEmpty()) {
            this.comments = entity.getComments().stream().map(comment -> {
                CommentResDTO commentResDTO = new CommentResDTO(comment);
                if (commentResDTO.getMemberId().equals(entity.getMember().getId())) {
                    commentResDTO.setEqualLogin();
                } return commentResDTO;
            }).collect(Collectors.toList());
        }

    }

    public void setEqualLogin() {
        this.isEqualLogin = true;
    }
}
