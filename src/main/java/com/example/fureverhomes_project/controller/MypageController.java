package com.example.fureverhomes_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fureverhomes/mypage")
public class MypageController {

    @GetMapping("/adopt")
    public String adoptPage() {
        return "html/dashboard/furever_myPage_adopt";
    }

    @GetMapping("/interest")
    public String interestPage() {
        return "html/dashboard/furever_myPage_Interest";
    }
}
