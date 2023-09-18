package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.enumClass.Sex;
import com.example.fureverhomes_project.entity.enumClass.Species;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Setter
public class Animal {

    @Id @Column(name = "ANIMAL_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //동물 아이디

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Species species; //종

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Sex sex; //성별

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean nueter; //중성화 여부

    @Column(nullable = false)
    private String name; //동물 이름

    @Lob
    private String health_condition; //건강상태

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'default.jpg'")
    private String picture; //동물 사진

    @Column(nullable = false)
    private String shelter_name; //보호소 이름

    private String shelter_tel; //보호소 연락처

}
