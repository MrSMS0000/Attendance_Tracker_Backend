package com.attendance.backend.controller;

import com.attendance.backend.model.Course;
import com.attendance.backend.model.Enrollment;
import com.attendance.backend.model.Student;
import com.attendance.backend.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
    @Autowired
    private EnrollmentService service;

    @GetMapping("/{id}/courses")
    public ResponseEntity<List<Course>> getCoursesByStudent(@PathVariable Long id){
        return new ResponseEntity<List<Course>>(service.getCoursesByStudent(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/other-courses")
    public ResponseEntity<List<Course>> getOtherCoursesByStudent(@PathVariable Long id){
        return new ResponseEntity<List<Course>>(service.getOtherCoursesByStudent(id), HttpStatus.OK);
    }

    @GetMapping("/{code}/students")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable String code){
        return new ResponseEntity<List<Student>>(service.getStudentsByCourse(code), HttpStatus.OK);
    }

    @PostMapping("/{id}/{code}")
    public ResponseEntity<Enrollment> addStudentCourse(@PathVariable Long id, @PathVariable String code){
        return new ResponseEntity<Enrollment>(service.addStudentCourse(id,code), HttpStatus.OK);
    }
}
