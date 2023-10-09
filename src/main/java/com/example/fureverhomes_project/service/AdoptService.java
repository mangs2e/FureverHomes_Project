package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.AdoptReqDTO;
import com.example.fureverhomes_project.dto.AdoptResDTO;
import com.example.fureverhomes_project.entity.Adopt;
import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.repository.AdoptRepository;
import com.example.fureverhomes_project.repository.AnimalRepository;
import com.example.fureverhomes_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdoptService {

    private final AdoptRepository adoptRepository;
    private final AnimalRepository animalRepository;
    private final MemberRepository memberRepository;

    //입양 신청
    @Transactional
    public Boolean insertAdopt(final AdoptReqDTO adoptReqDTO) {
        Animal animal = animalRepository.findById(adoptReqDTO.getAnimalId()).orElseThrow(() -> new EntityNotFoundException("Animal 객체가 없음"));
        Member member = memberRepository.findById(adoptReqDTO.getMemberId()).orElseThrow(() -> new EntityNotFoundException("Member 객체가 없음"));
        if (isExistAdopt(animal, member)) { //같은 입양 내역 존재
            return false;
        }
        adoptRepository.save(adoptReqDTO.toEntity(member, animal));
        return true;
    }

    //입양 내역이 존재하는지 확인
    private boolean isExistAdopt(final Animal animal, final Member member) {
        return adoptRepository.existsByAnimalAndMember(animal, member);
    }

    //입양 내역 조회
    public List<AdoptResDTO> selectAdoptList(final Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new EntityNotFoundException("Member 객체가 없음"));
        List<Adopt> adoptList = adoptRepository.findAllByMemberOrderByApplicationDate(member);
        return adoptList.stream().map(this::converToResDTO).collect(Collectors.toList());
    }

    private AdoptResDTO converToResDTO(final Adopt adopt) {
        return new AdoptResDTO(adopt.getId(), adopt.getApplicationDate(), adopt.getAnimal().getName(), adopt.getAdopt_status());
    }

    //입양 취소 변경하기
    @Transactional
    public void putAdopt(Map<String, String> params) {
        String cancel_reason = params.get("reason");
        Long adopt_id = Long.valueOf(params.get("adoptId"));
        Adopt adopt = adoptRepository.findById(adopt_id).orElseThrow(() -> new EntityNotFoundException("Adopt 객체가 없음"));
        adopt.updateCancelReason(cancel_reason);
        adoptRepository.save(adopt);
    }
}
