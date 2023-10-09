package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.dto.AnimalPageDTO;
import com.example.fureverhomes_project.dto.AnimalResDTO;
import com.example.fureverhomes_project.service.AnimalSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fureverhomes")
public class AnimalController {

    private final AnimalSearchService animalService;

    //검색 페이지
    @GetMapping("/animal")
    public String animalSearchPage() {
        return "html/furever_search";
    }

    //상세 페이지
    @GetMapping("/detail/{animal_id}")
    public String animalDetailPage() {
        return "html/furever_detail";
    }


    @RestController
    @RequestMapping("/fureverhomes")
    public class AnimalSearchRestController {

        //동물 목록 조회
        @GetMapping("/animal.list")
        public ResponseEntity<AnimalPageDTO> getAnimalList(
                @RequestParam Map<String, String> requestParams, Pageable pageRequest
                ) {
            Sort sort = Sort.by(Sort.Order.desc("regiDate")); // 필요한 정렬을 여기에 추가
            Pageable pageable = PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), sort);
            AnimalPageDTO animals = animalService.searchAnimal(requestParams, pageable);
            if(animals == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(animals);
        }

        //동물 상세 정보 조회
        @GetMapping("/detail/{animal_id}/getDetail")
        public ResponseEntity<AnimalResDTO> getDetail(@PathVariable("animal_id") Long animalId) {
            AnimalResDTO animal = animalService.animalDetail(animalId);
            if(animal == null) return ResponseEntity.internalServerError().build();
            return ResponseEntity.ok(animal);
        }
    }
}
