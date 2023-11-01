package com.attendance.backend.controller;

import com.attendance.backend.model.CourseAttendance;
import com.attendance.backend.model.StudentAttendance;
import com.attendance.backend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/student/{studentId}/{courseCode}")
    public ResponseEntity<String > markStudentAttendance(@PathVariable Long studentId, @PathVariable String courseCode){
        return new ResponseEntity<String>(attendanceService.markStudentAttendance(studentId,courseCode), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}/{courseCode}")
    public ResponseEntity<List<LocalDate>> getStudentAttendance(@PathVariable Long studentId, @PathVariable String courseCode){
        return new ResponseEntity<List<LocalDate>>(attendanceService.getStudentAttendance(studentId,courseCode), HttpStatus.OK);
    }

    @PostMapping("/course/{courseCode}")
    public ResponseEntity<String> startCourseAttendance(@PathVariable String courseCode){
        return new ResponseEntity<String>(attendanceService.startCourseAttendance(courseCode), HttpStatus.CREATED);
    }

    @DeleteMapping("/course/{courseCode}/{date}")
    public ResponseEntity<String> stopCourseAttendance(@PathVariable String courseCode, @PathVariable LocalDate date){
        return new ResponseEntity<String>(attendanceService.stopCourseAttendance(courseCode), HttpStatus.GONE);
    }

    @GetMapping("/course/{courseCode}")
    public ResponseEntity<List<LocalDate>> getCourseAttendance(@PathVariable String courseCode){
        return new ResponseEntity<List<LocalDate>>(attendanceService.getCourseAttendance(courseCode), HttpStatus.OK);
    }

}