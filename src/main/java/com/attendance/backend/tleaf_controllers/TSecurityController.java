package com.attendance.backend.tleaf_controllers;

import com.attendance.backend.model.GateEntry;
import com.attendance.backend.model.Status;
import com.attendance.backend.model.Student;
import com.attendance.backend.service.GateEntryService;
import com.attendance.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping("/security")
@PreAuthorize("hasAuthority('ROLE_SECURITY')")
public class TSecurityController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private GateEntryService gateEntryService;

    @GetMapping("/home")
    public String homePage(Model model){
        return "security/home";
    }

    @GetMapping("/pin")
    public String pinPage(Model model){
        String pin = gateEntryService.getPin();
        model.addAttribute("pin",pin);
        return "security/pin";
    }

    @GetMapping("/history")
    public String historyPage(Model model){
        return "security/history";
    }
    @GetMapping("/student")
    public String getStudent(@RequestParam("studentId") Long studentId, RedirectAttributes redirectAttributes){
        Student student = studentService.getStudent(studentId);
        redirectAttributes.addFlashAttribute("student",student);
        return "redirect:/security/history";
    }


}
