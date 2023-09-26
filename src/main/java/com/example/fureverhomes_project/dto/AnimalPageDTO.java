package com.example.fureverhomes_project.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AnimalPageDTO {
    private List<AnimalResDTO> animals;
    private int totalPage;
    private Long totalCount;

    public AnimalPageDTO(List<AnimalResDTO> animals, int totalPage, Long totalCount) {
        this.animals = animals;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
    }
}
