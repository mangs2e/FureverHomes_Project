package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Adopt;
import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdoptRepository extends JpaRepository<Adopt, Long> {
    Boolean existsByAnimalAndMember(Animal animal, Member member); //중복 등록 여부 확인

    List<Adopt> findAllByMemberOrderByApplicationDate(Member member); //입양 내역 전부 조회
}
