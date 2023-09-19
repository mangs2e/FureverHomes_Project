package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


//JpaRepository<엔티티 타입 클래스, 기본키 타입>
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //1. 회원 정보 저장
    //save();

    //2. 중복 이메일 확인 (회원 수 카운팅)
    long countByEmail(String email);

    //3. 이메일 인증 여부 확인
    Member findByEmail(String email);
    boolean existsByEmail(String email);

    //4. 회원 정보 삭제
    void deleteByEmail(String email);
}
