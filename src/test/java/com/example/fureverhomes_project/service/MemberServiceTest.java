package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.MailDTO;
import com.example.fureverhomes_project.dto.MemberReqDto;
import com.example.fureverhomes_project.entity.enumClass.Sex;
import com.example.fureverhomes_project.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService; //의존성 주입

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender mailSender;

    public MemberReqDto createMemberTest1() {
        MemberReqDto memberReqDto = new MemberReqDto();
        memberReqDto.setEmail("test1@test.com");
        memberReqDto.setName("test1");
        memberReqDto.setBirth(LocalDate.now());
        memberReqDto.setPassword("test123");
        memberReqDto.setSex(Sex.M);
        return memberReqDto;
    }

    public MailDTO createMail() {
        MailDTO mailDTO = new MailDTO();
        mailDTO.setEmail("d@test.com");
        return mailDTO;
    }


    @Test
    @DisplayName("회원가입 테스트 - 신규회원")
    public void saveNewMemberTest() {
        Long memberId = memberService.saveMember(createMemberTest1());
        assertNotNull(memberId);
    }

    @Test
    @DisplayName("회원가입 테스트 - 이메일 인증 받지 않은 회원")
    public void saveNotAuthTest() {
        MemberReqDto member1 = createMemberTest1();
        //이메일 인증이 안된 회원 정보 저장
        Long memberId1 = memberService.saveMember(member1);
        assertNotNull(memberId1);
        System.out.println("가입 성공! 인증x");

        //중복 이메일로 다시 회원가입 시 (이전 정보를 지우고 다시 회원정보 저장)
        boolean isDuplicate = memberService.isDuplicateMember(member1.getEmail());
        assertFalse(isDuplicate);
        //새로운 회원 정보 저장
        Long newMember = memberService.saveMember(member1);
        assertNotNull(newMember);
    }

}