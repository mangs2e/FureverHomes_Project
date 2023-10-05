package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Board;
import com.example.fureverhomes_project.entity.Member;
import lombok.Getter;

@Getter
public class BoardReqDTO {
    private Long memberId;
    private String title;
    private String content;
    private String writer;

    public Board toEntity(Member member) {
        return Board.builder()
                .member(member)
                .title(title)
                .content(content)
                .views(0).build();
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
