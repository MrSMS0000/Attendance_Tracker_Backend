package com.attendance.backend.controller;


import com.attendance.backend.model.Faculty;
import com.attendance.backend.service.FacultyService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculties")
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @GetMapping
    public ResponseEntity<List<Faculty>> getFaculties(){
        return new ResponseEntity<List<Faculty>>(facultyService.getFaculties(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id){
        return new ResponseEntity<Faculty>(facultyService.getFaculty(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addFaculty(@RequestBody Faculty faculty){
        return new ResponseEntity<String>(facultyService.addFaculty(faculty), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<DeleteResult> deleteFaculty(@RequestBody Faculty faculty){
        return new ResponseEntity<DeleteResult>(facultyService.removeFaculty(faculty), HttpStatus.GONE);
    }
}

