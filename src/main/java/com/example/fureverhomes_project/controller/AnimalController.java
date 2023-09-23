package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.service.AnimalSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        public ResponseEntity<String> getAnimalList() {
            String animals = animalService.selectListByRegiDate();
            if(animals == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.ok(animals);
        }

        //동물 상세 정보 조회
        @GetMapping("/detail/{animal_id}/getDetail")
        public ResponseEntity<String> getDetail(@PathVariable("animal_id") Long animalId) {
            String animal = animalService.animalDetail(animalId);
            if(animal == null) return ResponseEntity.internalServerError().build();
            return ResponseEntity.ok(animal);
        }
    }
}
