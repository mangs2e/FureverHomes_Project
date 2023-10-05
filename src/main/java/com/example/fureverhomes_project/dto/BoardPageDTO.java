package com.example.fureverhomes_project.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class BoardPageDTO {
    private List<BoardResDTO> boardList;
    private int totalPage;
    private Long totalCount;

    public BoardPageDTO(List<BoardResDTO> boardList, int totalPage, Long totalCount) {
        this.boardList = boardList;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
    }
}
