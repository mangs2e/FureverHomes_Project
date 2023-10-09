package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Board;
import com.example.fureverhomes_project.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardReqDTO {
    private Long boardId;
    private Long memberId;
    private String title;
    private String content;
    private String writer;

    @Builder
    public BoardReqDTO(Long boardId, Long memberId, String title, String content) {
        this.boardId = boardId;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
    }

    public Board toEntity(Member member) {
        return Board.builder()
                .member(member)
                .title(title)
                .content(content)
                .views(0).build();
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content).build();
    }

    public void boardSearchKeyword(String searchType, String searchKeyword) {
        if (searchType.equals("title")) {
            this.title = searchKeyword;
        }
        if (searchType.equals("writer")) {
            this.writer = searchKeyword;
        }
        if (searchType.equals("content")) {
            this.content = searchKeyword;
        }
    }
}
