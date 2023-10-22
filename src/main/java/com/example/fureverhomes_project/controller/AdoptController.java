package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.dto.AdoptReqDTO;
import com.example.fureverhomes_project.dto.AdoptResDTO;
import com.example.fureverhomes_project.service.AdoptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fureverhomes")
public class AdoptController {

    private final AdoptService adoptService;

    //입양 신청
    @PostMapping("/detail/{animal_id}/adopt")
    public ResponseEntity<Object> insertAdopt(@RequestBody AdoptReqDTO adoptReqDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        adoptReqDTO.updateMemberId((Long) session.getAttribute("loginMember"));
        if(adoptService.insertAdopt(adoptReqDTO)) return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    //입양 내역 조회
    @GetMapping("/mypage/adopt.list")
    public ResponseEntity<List<AdoptResDTO>> selectAdoptList(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long memberId = (Long) session.getAttribute("loginMember");
        List<AdoptResDTO> adoptResDTO = adoptService.selectAdoptList(memberId);
        return ResponseEntity.ok(adoptResDTO);
    }

    //입양 신청 취소
    @PutMapping("/mypage/adopt.cancel")
    public ResponseEntity<Object> putAdopt(@RequestBody Map<String, String> params) {
        adoptService.putAdopt(params);
        return ResponseEntity.ok().build();
    }
}
