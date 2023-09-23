package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.AnimalResDTO;
import com.example.fureverhomes_project.entity.Animal;
import com.example.fureverhomes_project.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalSearchService {

    private final AnimalRepository animalRepository;

    //등록날짜 기준으로 전체 동물 리스트 조회
    public String selectListByRegiDate() {
        List<Animal> animals = animalRepository.findAll(Sort.by(Sort.Direction.DESC, "regiDate"));

        List<AnimalResDTO> animalResDTO = animals.stream() //리스트 animals를 스트림으로 변환해서
                .map(this::converToResDTO) //각 요소에 대해 converToResDTO 메소드를 적용해 (엔티티 -> DTO)
                .collect(Collectors.toList()); //변환된 DTO를 다시 리스트로 수집

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();

        for (AnimalResDTO dto : animalResDTO) {
            JSONObject object = new JSONObject();
            object.put("id", dto.getId());
            object.put("name", dto.getName());
            object.put("picture", dto.getPicture());
            object.put("region", dto.getRegion().toString());
            object.put("sex", dto.getSex().toString());
            object.put("age", dto.getAge());

            jsonArray.add(object);
        }

        jsonObject.put("animal_list", jsonArray);

        return jsonObject.toJSONString();

    }

    //resDTO 변환
    private AnimalResDTO converToResDTO(final Animal animal) {
        AnimalResDTO animalDTO =
                new AnimalResDTO(animal.getId(), animal.getName(), animal.getRegion(), animal.getSex(), animal.getAge(), animal.getPicture(),
                        animal.getNeuter(), animal.getHealth_condition(), animal.getShelter_name(), animal.getShelter_tel(), animal.getRegiDate(), animal.getPersonality());
        return animalDTO;
    }

    //하나의 동물 결과 조회
    public String animalDetail(final Long animalId) {
        Optional<Animal> animalOptional = animalRepository.findById(animalId);
        if (animalOptional.isPresent()) {
            System.out.println("DTO 변환 시작");
            Animal animal = animalOptional.get();
            AnimalResDTO animalResDTO = converToResDTO(animal);

            JSONObject object = new JSONObject();
            object.put("id", animalResDTO.getId());
            object.put("name", animalResDTO.getName());
            object.put("picture", animalResDTO.getPicture());
            object.put("region", animalResDTO.getRegion().toString());
            object.put("sex", animalResDTO.getSex().toString());
            object.put("age", animalResDTO.getAge());
            object.put("personality", animalResDTO.getPersonality());
            object.put("health", animalResDTO.getHealth_condition());
            object.put("neuter", animalResDTO.getNeuter());
            object.put("shelter_name", animalResDTO.getShelter_name());
            object.put("shelter_tel", animalResDTO.getShelter_tel());

            return object.toJSONString();
        } else {
            return null;
        }
    }
}
