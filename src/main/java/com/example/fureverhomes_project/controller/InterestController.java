package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fureverhomes")
public class InterestController {

    private final InterestService interestService;

    //관심 동물 등록
    @PostMapping("/detail/{animal_id}/interest")
    public ResponseEntity<Object> insertInterestAnimal(@RequestBody Map<String, String> animalId, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("loginMember");
        if (interestService.insertInterest(Long.valueOf(animalId.get("animalId")), memberId)) {
            return ResponseEntity.ok().build();
        } return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
