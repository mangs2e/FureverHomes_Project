package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.dto.AdoptReqDTO;
import com.example.fureverhomes_project.service.AdoptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fureverhomes")
public class AdoptController {

    private final AdoptService adoptService;

    @PostMapping("/detail/{animal_id}/adopt")
    public ResponseEntity<Object> insertAdopt(@RequestBody AdoptReqDTO adoptReqDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        adoptReqDTO.updateMemberId((Long) session.getAttribute("loginMember"));
        if(adoptService.insertAdopt(adoptReqDTO)) return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
