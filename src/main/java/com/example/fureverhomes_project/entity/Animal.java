package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.enumClass.Region;
import com.example.fureverhomes_project.entity.enumClass.Sex;
import com.example.fureverhomes_project.entity.enumClass.Species;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private Boolean neuter; //중성화 여부

    @Column(nullable = false    )
    private String name; //동물 이름

    private int age; //나이

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Region region; //보호 지역

    @Lob
    private String health_condition; //건강상태

    @Lob
    private String personality; //성격

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT 'default.jpg'")
    private String picture; //동물 사진

    @Column(nullable = false)
    private String shelter_name; //보호소 이름

    private String shelter_tel; //보호소 연락처

    @Column(nullable = false)
    private LocalDate regiDate = LocalDate.now(); //등록날짜

    @OneToMany(mappedBy = "animal")
    private Set<Interest> interests = new HashSet<>();

}
