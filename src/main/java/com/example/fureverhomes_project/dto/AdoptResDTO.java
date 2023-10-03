package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.enumClass.AdoptStatus;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AdoptResDTO {
    //날짜, 이름, 상태
    private Long id;
    private LocalDate applicationDate;
    private String name;
    private AdoptStatus adoptStatus;

    public AdoptResDTO(Long id, LocalDate applicationDate, String name, AdoptStatus adoptStatus) {
        this.id = id;
        this.applicationDate = applicationDate;
        this.name = name;
        this.adoptStatus = adoptStatus;
    }
}
