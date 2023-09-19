package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.MemberReqDto;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
        long existEmail = memberRepository.countByEmail(email);
        if (existEmail != 0) {
            return checkEmailAuth(email);
        }else return false;
    }

    //이메일 인증 여부 확인
    @Transactional
    public boolean checkEmailAuth(final String email) {
        Member member = memberRepository.findByEmail(email);
        if (member != null && member.getEmail_auth()) {
            System.out.println("이미 가입된 회원입니다.");
            return true;
        } else {
            System.out.println("이메일 인증을 받지 않은 회원입니다. 회원 정보를 삭제합니다.");
            //회원정보삭제
            memberRepository.deleteByEmail(email);
            return false;
        }
    }
}
