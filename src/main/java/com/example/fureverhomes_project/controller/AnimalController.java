package com.example.fureverhomes_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/fureverhomes")
public class AnimalController {

    @GetMapping("/animal")
    public String animalSearch() {
        return "html/furever_search";
    }
}
