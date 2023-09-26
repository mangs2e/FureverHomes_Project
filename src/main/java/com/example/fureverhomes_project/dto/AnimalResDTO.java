package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.enumClass.Region;
import com.example.fureverhomes_project.entity.enumClass.Sex;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class AnimalResDTO {
    private Long id; //아이디
    private String name; //이름
    private Region region; //보호지역
    private Sex sex; //성별
    private int age; //나이
    private String picture; //사진

    private Boolean neuter; //중성화여부
    private String health_condition; //건강상태
    private String shelter_name; //보호소 이름
    private String shelter_tel; //보호소 번호
    private LocalDate regiDate; //등록날짜
    private String personality; //성격

    public AnimalResDTO(Long id, String name, Region region, Sex sex, int age, String picture, Boolean neuter, String health_condition, String shelter_name, String shelter_tel, LocalDate regiDate, String personality) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.sex = sex;
        this.age = age;
        this.picture = picture;
        this.neuter = neuter;
        this.health_condition = health_condition;
        this.shelter_name = shelter_name;
        this.shelter_tel = shelter_tel;
        this.regiDate = regiDate;
        this.personality = personality;
    }
}
