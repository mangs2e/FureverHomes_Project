package com.example.fureverhomes_project.dto;

import lombok.Getter;

@Getter
public class InterestResDTO {
    private Long interestId;
    private String name;
    private Long animalId;

    public InterestResDTO(Long interestId, String name, Long animalId) {
        this.interestId = interestId;
        this.name = name;
        this.animalId = animalId;
    }
}
