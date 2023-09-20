package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//JpaRepository<엔티티 타입 클래스, 기본키 타입>
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //회원 정보 저장
    //save();

    //중복 이메일, 이메일 인증 여부 학인
    Member findByEmail(String email);

    //회원 정보 삭제
    void deleteByEmail(String email);

    //이메일 인증 - 2. 비교 후 1로 변경
}
