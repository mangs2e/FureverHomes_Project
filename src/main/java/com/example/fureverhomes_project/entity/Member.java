package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.enumClass.Sex;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id; //pk

    @Column(nullable = false)
    private String email; //회원 아이디

    @Column(nullable = false)
    private String password; //비밀번호

    @Column(nullable = false, length = 50)
    private String name; //이름

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Sex sex; //성별

    @Column(nullable = false)
    private LocalDate birth; //생년월일

    @Column(nullable = false)
    private LocalDate regi_date; //가입날짜

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    @ColumnDefault("false")
    private Boolean email_auth; //이메일 인증여부

    @ManyToMany
    @JoinTable(name = "INTEREST_ANIMAL",
            joinColumns = @JoinColumn(name = "MEMBER_ID"), inverseJoinColumns = @JoinColumn(name = "ANIMAL_ID"))
    private List<Animal> animals = new ArrayList<>(); //연관관계 매핑 - 관심동물 (다대다 단방향일듯)

}
