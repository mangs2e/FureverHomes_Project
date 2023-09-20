package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.MailDTO;
import com.example.fureverhomes_project.dto.MemberReqDto;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    //회원 저장 (회원가입)
    @Transactional
    public Long saveMember(final MemberReqDto memberDto) {

        //중복회원인 경우
        if (isDuplicateMember(memberDto.getEmail())) {
            return null;
        }

        //중복회원이 아닌 경우
        System.out.println("신규회원입니다.");
        memberDto.encodingPassword(passwordEncoder);
        Member member = memberRepository.save(memberDto.toEntity());
        return member.getId();
    }

    //중복여부 확인
    @Transactional
    public boolean isDuplicateMember(final String email) {
        Member existingMember = memberRepository.findByEmail(email);
        if (existingMember != null && existingMember.getEmail_auth() == 1) { //회원 존재 & 인증도 처리
            return true;
        } else if (existingMember != null && existingMember.getEmail_auth() == 0) { //회원 존재 & 인증 처리 안함
            System.out.println("이메일 인증을 받지 않은 회원입니다. 회원 정보를 삭제합니다.");
            //회원정보삭제
            deleteMember(email);
        } return false; //회원 정보 없음
    }

    @Transactional
    public void deleteMember(final String email) {
        memberRepository.deleteByEmail(email);
    }

    //인증 메일 전송 - 인증코드 만들어서 db 저장
    @Transactional
    public Boolean sendEmailWithAuthCode(final MailDTO mailDTO) {
        String email = mailDTO.getEmail();
        int authCode = makeAuthCode();

        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            member.updateEmailAuth(authCode);
            memberRepository.save(member);
            return sendMail(email, authCode);
        } return false;
    }

    //인증 메일 전송 - 메일 보내기
    private Boolean sendMail(String email, int authCode) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("fureverhomes 인증 메일입니다.");
        message.setText("인증코드: " + authCode);

        System.out.println("[message] "+ message);

        try{
            mailSender.send(message);
            System.out.println(email + " 메일 전송 성공");
            return true;
        } catch (MailException e){
            e.printStackTrace();
            System.out.println(email + " 메일 전송 실패");
            return false;
        }

    }

    //인증 메일 전송 - 랜덤 코드 생성
    private int makeAuthCode() {
        Random random = new Random();
        return random.nextInt(888888) + 111111; // 범위: 111111~999999
    }

    //인증 성공 여부 확인
    @Transactional
    public Boolean isSuccessAuth(Map<String,String> mapParam) {
        String email = mapParam.get("email");
        String authCode = mapParam.get("emailCode");

        Member member = memberRepository.findByEmail(email);
        int getAuthCode = member.getEmail_auth();
        System.out.println("입력받은 값"+authCode);
        System.out.println("db안에 값"+getAuthCode);

        if (authCode.equals(String.valueOf(getAuthCode))) {
            member.updateEmailAuth(1);
            memberRepository.save(member);
            return true;
        } return false;
    }

}
