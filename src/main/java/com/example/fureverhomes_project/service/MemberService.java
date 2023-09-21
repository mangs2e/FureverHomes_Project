package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.MailDTO;
import com.example.fureverhomes_project.dto.MemberReqDto;
import com.example.fureverhomes_project.dto.MemberResDto;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private Map<String, Long> linkExpirationMap = new ConcurrentHashMap<>();

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
    private Boolean sendMail(final String email, final int authCode) {
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
    public Boolean isSuccessAuth(final Map<String,String> mapParam) {
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

    //로그인
    public Long singin(final Map<String,String> mapParam) {

        String email = mapParam.get("email");
        String password = mapParam.get("password");

        Member member = memberRepository.findByEmail(email);

        String memberPwd = (member == null) ? "" : member.getPassword();

        if(member == null || (!passwordEncoder.matches(password, memberPwd))){
            return null;
        } return member.getId();
    }

    //비밀번호 재설정 링크 전송
    public Boolean sendLinkMail(@RequestBody MailDTO mailDTO) throws MessagingException {
        String email = mailDTO.getEmail();

        Member member = memberRepository.findByEmail(email);
        System.out.println(member.toString());
        if (member != null) {
            return sendMail(email);
        } return null;
    }

    //링크 담은 메일 전송
    private Boolean sendMail(final String email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String linkId = UUID.randomUUID().toString();
        String link = "http://localhost:8081/fureverhomes/signin/changePassword?linkId=" + linkId; //페이지 링크

        long expirationTime = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5); // 만료 시간 설정
        linkExpirationMap.put(linkId, expirationTime); // 링크별 만료 시간 저장

        String mailContent = "<html><body>" +
                "<p>아래 링크로 들어가 비밀번호를 재설정해주세요.</p>" +
                "<br><a href='" + link + "'>5분이 지나면 링크가 만료됩니다.</a>" +
                "</body></html>";

        helper.setTo(email);
        helper.setSubject("fureverhomes 비밀번호 재설정 안내");
        helper.setText(mailContent, true);

        System.out.println("[message] "+ message);

        try {
            mailSender.send(message);
            System.out.println("이메일 전송 성공");
            return true;
        }catch(MailException e) {
            System.out.println("이메일 전송 실패");
            return false;
        }
    }

    //링크 만료 시간 체크
    public Long getLinkExpirationMap(String linkId) {
        return linkExpirationMap.get(linkId);
    }

    @Transactional
    public Boolean changePassword(final Map<String, String> mapParam) {
        String email = mapParam.get("email");
        String newPassword = mapParam.get("password");

        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            member.updatePassword(passwordEncoder.encode(newPassword));
            memberRepository.save(member);
            return true;
        } return false;
    }

}
