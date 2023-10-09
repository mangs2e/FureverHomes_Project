package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResDTO {
    private Long id;
    private Long memberId;
    private String writer;
    private String comment;
    private LocalDateTime createDate;
    private boolean isEqualLogin;

    public CommentResDTO(Comment comment) {
        this.id = comment.getId();
        this.memberId = comment.getMember().getId();
        this.writer = comment.getMember().getName();
        this.comment = comment.getComment();
        this.createDate = comment.getUploadDate();
    }

    public void setEqualLogin() {
        this.isEqualLogin = true;
    }
}
