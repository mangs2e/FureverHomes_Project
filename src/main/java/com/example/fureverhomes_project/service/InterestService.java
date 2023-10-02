package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.Interest;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.repository.AnimalRepository;
import com.example.fureverhomes_project.repository.InterestRepository;
import com.example.fureverhomes_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final AnimalRepository animalRepository;
    private final MemberRepository memberRepository;

    //관심동물 등록
    @Transactional
    public Boolean insertInterest(final Long animalId, final Long memberId) {
        Animal animal = animalRepository.findById(animalId).orElseThrow(() -> new EntityNotFoundException("Animal 객체가 없음"));
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member 객체가 없음"));
        if (isExistAnimalAndMember(animal, member)) {
            return false;
        }
        interestRepository.save(new Interest(member, animal));
        return true;
    }

    //관심동물 유무확인
    public Boolean isExistAnimalAndMember(final Animal animal, final Member member) {
        return interestRepository.existsByAnimalAndMember(animal, member);
    }

}
