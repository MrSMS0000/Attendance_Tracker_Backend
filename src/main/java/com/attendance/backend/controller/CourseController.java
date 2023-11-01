package com.attendance.backend.controller;

import com.attendance.backend.model.Course;
import com.attendance.backend.service.CourseService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getCourses(){
        return new ResponseEntity<List<Course>>(courseService.getCourses(), HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Course> getCourse(@PathVariable String code){
        return new ResponseEntity<Course>(courseService.getCourse(code), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addCourse(@RequestBody Course course){
        return new ResponseEntity<String>(courseService.addCourse(course), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<DeleteResult> deleteCourse(@RequestBody Course course){
        return new ResponseEntity<DeleteResult>(courseService.removeCourse(course), HttpStatus.GONE);
    }

    @GetMapping("/faculty/{id}")
    public ResponseEntity<List<Course>> getCoursesByFaculty(@PathVariable Long id){
        return new ResponseEntity<List<Course>>(courseService.getCoursesByFaculty(id), HttpStatus.OK);
    }

    @GetMapping("/other/faculty/{id}")
    public ResponseEntity<List<Course>> getOtherCoursesByFaculty(@PathVariable Long id){
        return new ResponseEntity<List<Course>>(courseService.getOtherCoursesByFaculty(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/{code}")
    public ResponseEntity<Course> addFacultyCourse(@PathVariable Long id, @PathVariable String code){
        return new ResponseEntity<Course>(courseService.addFacultyCourse(id,code), HttpStatus.CREATED);
    }
}