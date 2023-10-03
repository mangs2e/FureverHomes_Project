package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.enumClass.Sex;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
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
    @Column(nullable = false, length = 10)
    private Sex sex; //성별

    @Column(nullable = false)
    private LocalDate birth; //생년월일

    @Column(nullable = false)
    private LocalDate regi_date = LocalDate.now(); //가입날짜

    @Column(nullable = false)
    private int email_auth; //이메일 인증여부

    @OneToMany(mappedBy = "member")
    private Set<Interest> interests = new HashSet<>();

    @Builder
    public Member(String email, String password, String name, Sex sex, LocalDate birth, int email_auth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.email_auth = email_auth;
    }

    public void updateEmailAuth(int authCode) {
        this.email_auth = authCode;
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
