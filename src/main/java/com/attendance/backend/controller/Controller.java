package com.attendance.backend.controller;

import com.attendance.backend.model.GateEntry;
import com.attendance.backend.model.Student;
import com.attendance.backend.service.CourseService;
import com.attendance.backend.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class Controller {
    @Autowired
    private Service service;

    @GetMapping("/getStudentAttendanceHistory/{studentId}")
    public List<GateEntry> getStudentAttendanceHistory(@PathVariable Long studentId){
        return service.getStudentGateAttendanceHistory(studentId);
    }

    // testing baaki
    @GetMapping("/getStudent/{studentId}")
    public Student getStudent(@PathVariable Long studentId){
        return service.getStudent(studentId);
    }

    @PutMapping("/{studentId}")
    public void addStudentGateAttendance(@PathVariable Long studentId, @RequestBody GateEntry newGateEntry){
        newGateEntry.setDate(LocalDate.now());
        newGateEntry.setTime(LocalTime.now());
        service.addStudentGateAttendance(studentId,newGateEntry);
    }

    // UTILITY
    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return service.addStudent(student);
    }

}

