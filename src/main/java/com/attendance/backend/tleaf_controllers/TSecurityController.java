package com.attendance.backend.tleaf_controllers;

import com.attendance.backend.model.GateEntry;
import com.attendance.backend.model.Status;
import com.attendance.backend.model.Student;
import com.attendance.backend.service.GateEntryService;
import com.attendance.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/student")
    public String getStudent(@RequestParam("studentId") Long studentId, RedirectAttributes redirectAttributes){
        Student student = studentService.getStudent(studentId);
        redirectAttributes.addFlashAttribute("student",student);
        return "redirect:/security/home";
    }

    @PostMapping("/entry/{id}")
    public String addGateEntry(@PathVariable Long id, @RequestParam String reason, @RequestParam Status status,  RedirectAttributes redirectAttributes){
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.now();
        GateEntry gateEntry = new GateEntry(today, time, status, reason);
        gateEntryService.addGateEntry(gateEntry,id);
        Student student = studentService.getStudent(id);
        redirectAttributes.addFlashAttribute("student",student);
        return "redirect:/security/home";
    }

}
