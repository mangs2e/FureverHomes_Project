package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.Interest;
import com.example.fureverhomes_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long> {
    //일치여부 확인
    Boolean existsByAnimalAndMember(Animal animal, Member member);

    //멤버의 좋아요 내역 조회
    List<Interest> findAllByMemberOrderById(Member member);
}
