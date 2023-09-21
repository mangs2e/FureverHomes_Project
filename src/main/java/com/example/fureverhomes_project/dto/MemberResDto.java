package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.entity.enumClass.Sex;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class MemberResDto {

    private Long id; //pk
    private String email; //회원 아이디
    private String password; //비밀번호
    private String name; //이름
    private Sex sex; //성별
    private LocalDate birth; //생년월일
    private LocalDate regi_date; //가입날짜
    private int email_auth; //이메일 인증여부
    private List<Animal> animals; //관심동물

    public void clearPassword() {
        this.password = "";
    }
}
