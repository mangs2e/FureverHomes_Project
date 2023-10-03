package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.AnimalPageDTO;
import com.example.fureverhomes_project.dto.AnimalResDTO;
import com.example.fureverhomes_project.dto.AnimalSearchDTO;
import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.entity.enumClass.Region;
import com.example.fureverhomes_project.entity.enumClass.Sex;
import com.example.fureverhomes_project.entity.enumClass.Species;
import com.example.fureverhomes_project.repository.AnimalRepository;
import com.querydsl.core.BooleanBuilder;
import static com.example.fureverhomes_project.entity.QAnimal.animal;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalSearchService {

    private final AnimalRepository animalRepository;
    private final EntityManager em;

    public AnimalPageDTO searchAnimal(final Map<String, String> requestparams, Pageable pageable) {
        AnimalSearchDTO animalSearchDTO = new AnimalSearchDTO();
        String species = requestparams.get("species");
        String sex = requestparams.get("sex");
        String region = requestparams.get("region");

        if (species != null) {
            animalSearchDTO.setSpecies(Species.valueOf(species));
        }
        if (sex != null) {
            animalSearchDTO.setSex(Sex.valueOf(sex));
        }
        if (region != null) {
            animalSearchDTO.setRegion(Region.valueOf(region));
        }

        Page<Animal> animals = findAllBySearchKeword(animalSearchDTO, pageable);

        List<AnimalResDTO> animalResDTO = animals.getContent().stream() //리스트 animals를 스트림으로 변환해서
                .map(this::converToResDTO) //각 요소에 대해 converToResDTO 메소드를 적용해 (엔티티 -> DTO)
                .collect(Collectors.toList()); //변환된 DTO를 다시 리스트로 수집

        return new AnimalPageDTO(animalResDTO, animals.getTotalPages(), animals.getTotalElements());
    }



    //resDTO 변환
    private AnimalResDTO converToResDTO(final Animal animal) {
        System.out.println("ResDTO 변환!");
        AnimalResDTO animalDTO =
                new AnimalResDTO(animal.getId(), animal.getName(), animal.getRegion(), animal.getSex(), animal.getAge(), animal.getPicture(),
                        animal.getNeuter(), animal.getHealth_condition(), animal.getShelter_name(), animal.getShelter_tel(), animal.getRegiDate(), animal.getPersonality());
        return animalDTO;
    }

    //검색조건으로 검색하기
    private Page<Animal> findAllBySearchKeword(final AnimalSearchDTO animalSearchDTO, Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        BooleanBuilder builder = new BooleanBuilder();

        if (animalSearchDTO.getSpecies() != null) {
            builder.and(animal.species.stringValue().eq(animalSearchDTO.getSpecies().toString()));
        }
        if (animalSearchDTO.getSex() != null) {
            builder.and(animal.sex.stringValue().eq(animalSearchDTO.getSex().toString()));
        }
        if (animalSearchDTO.getRegion() != null) {
            builder.and(animal.region.stringValue().eq(animalSearchDTO.getRegion().toString()));
        }


        List<Animal> animals = queryFactory
                .selectFrom(animal)
                .where(builder)
                .orderBy(animal.regiDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long totalCount = queryFactory.select(animal.count()).from(animal).where(builder).fetchFirst();

        return new PageImpl<>(animals, pageable, totalCount);
    }

    //하나의 동물 결과 조회
    public AnimalResDTO animalDetail(final Long animalId) {
        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        if (animalOptional.isPresent()) {
            Animal animal = animalOptional.get();
            return converToResDTO(animal);
        } else {
            return null;
        }
    }
}
