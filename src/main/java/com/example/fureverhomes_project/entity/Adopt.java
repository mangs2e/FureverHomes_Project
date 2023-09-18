package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.enumClass.AdoptStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Setter
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
    @ColumnDefault("false")
    private String breeding; //동물 길러본 경험

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
}
