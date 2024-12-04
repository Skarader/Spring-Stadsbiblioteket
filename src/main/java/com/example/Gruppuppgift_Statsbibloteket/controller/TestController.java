package com.example.Gruppuppgift_Statsbibloteket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}
