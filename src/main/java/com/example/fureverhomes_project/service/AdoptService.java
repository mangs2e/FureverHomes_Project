package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.AdoptReqDTO;
import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.repository.AdoptRepository;
import com.example.fureverhomes_project.repository.AnimalRepository;
import com.example.fureverhomes_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdoptService {

    private final AdoptRepository adoptRepository;
    private final AnimalRepository animalRepository;
    private final MemberRepository memberRepository;

    //입양 신청
    @Transactional
    public Boolean insertAdopt(AdoptReqDTO adoptReqDTO) {
        Animal animal = animalRepository.findById(adoptReqDTO.getAnimalId()).orElseThrow(() -> new EntityNotFoundException("Animal 객체가 없음"));
        Member member = memberRepository.findById(adoptReqDTO.getMemberId()).orElseThrow(() -> new EntityNotFoundException("Member 객체가 없음"));
        if (isExistAdopt(animal, member)) { //같은 입양 내역 존재
            return false;
        }
        adoptRepository.save(adoptReqDTO.toEntity(member, animal));
        return true;
    }

    //입양 내역이 존재하는지 확인
    private boolean isExistAdopt(Animal animal, Member member) {
        return adoptRepository.existsByAnimalAndMember(animal, member);
    }
}
