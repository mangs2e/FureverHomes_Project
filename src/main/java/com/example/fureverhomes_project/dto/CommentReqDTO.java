package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Board;
import com.example.fureverhomes_project.entity.Comment;
import com.example.fureverhomes_project.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentReqDTO {

    private Long member_id;
    private Long board_id;
    private String comment;

    @Builder
    public CommentReqDTO(Long member_id, Long board_id, String comment) {
        this.member_id = member_id;
        this.board_id = board_id;
        this.comment = comment;
    }

    public Comment toEntity(Member member, Board board) {
        return Comment.builder()
                .member(member)
                .board(board)
                .comment(this.comment)
                .build();
    }
}
