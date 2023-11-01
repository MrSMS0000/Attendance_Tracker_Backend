package com.attendance.backend.tleaf_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class THelloController {
    @GetMapping("/")
    public String homePage(){
        return "index";
    }
}
