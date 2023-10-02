package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.enumClass.AdoptStatus;
import com.example.fureverhomes_project.entity.enumClass.Sex;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Adopt {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ADOPT_ID")
    private Long id; //입양 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //연관관계 매핑 - 멤버

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANIMAL_ID")
    private Animal animal; //연관관계 매핑 - 동물

    @Column(nullable = false)
    private String phonenum; //입양자 연락처

    @Column(nullable = false)
    private String contact_time; //연락가능시간

    @Column(nullable = false)
    private String residence; //거주지

    private String job; //직업

    @Column(nullable = false, columnDefinition = "TINYINT(1)", name = "BREEDING_EXP")
    private Boolean breeding; //동물 길러본 경험

    @Lob
    @Column(nullable = false)
    private String adopt_reason; //입양 이유

    @Lob
    private String add_comment; //추가 전달사항

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdoptStatus adopt_status; //입양 신청 상태

    @Lob
    private String cancel_reason; //입양 취소 이유

    @Builder
    public Adopt(Member member, Animal animal, String phonenum, String contact_time, String residence, String job, Boolean breeding, String adopt_reason, String add_comment, AdoptStatus adopt_status) {
        this.member = member;
        this.animal = animal;
        this.phonenum = phonenum;
        this.contact_time = contact_time;
        this.residence = residence;
        this.job = job;
        this.breeding = breeding;
        this.adopt_reason = adopt_reason;
        this.add_comment = add_comment;
        this.adopt_status = adopt_status;
    }
}
