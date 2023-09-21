package com.example.fureverhomes_project.dto;

import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.entity.enumClass.Sex;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;

//회원가입 정보가 넘어오는 dto
@Data
@NoArgsConstructor
public class MemberReqDto {
    private String email; //이메일
    private String name; //이름
    private String password; //비밀번호
    private Sex sex; //성별
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private LocalDate birth; //생년월일
//    private boolean emailAuth; //이메일 인증여부

    public void encodingPassword(PasswordEncoder passwordEncoder) {
        if(ObjectUtils.isEmpty(password)) return;
        password = passwordEncoder.encode(password);
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .name(name)
                .password(password)
                .sex(sex)
                .birth(birth)
                .email_auth(0)
                .build();
    }

    public Member updatePwd(String password) {
        return Member.builder()
                .password(password)
                .build();
    }
}
