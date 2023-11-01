package com.attendance.backend.controller;


import com.attendance.backend.model.Student;
import com.attendance.backend.security.PersonToUserDetails;
import com.attendance.backend.service.StudentService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(){
        return new ResponseEntity<List<Student>>(studentService.getStudents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id){
        return new ResponseEntity<Student>(studentService.getStudent(id), HttpStatus.OK);
    }

    @GetMapping("/hello")
    public String helloStudent(@AuthenticationPrincipal PersonToUserDetails person){
        return "Hello " + person.getUsername();
    }
    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody Student student){
        return new ResponseEntity<String>(studentService.addStudent(student), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<DeleteResult> deleteStudent(@RequestBody Student student){
        return new ResponseEntity<DeleteResult>(studentService.removeStudent(student), HttpStatus.GONE);
    }
}

