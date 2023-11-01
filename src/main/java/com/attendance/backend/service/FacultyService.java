package com.attendance.backend.service;

import com.attendance.backend.model.Faculty;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyService {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<Faculty> getFaculties(){
        return template.findAll(Faculty.class,"faculties");
    }
    public Faculty getFaculty(Long id){
        return template.findById(id, Faculty.class, "faculties");
    }
    public String addFaculty(Faculty faculty){
        if(template.findById(faculty.getId(), Faculty.class, "faculties")!=null){
            return "Id "+faculty.getId()+" already exist.";
        }
        faculty.setRoles("ROLE_FACULTY");
        faculty.setPassword(passwordEncoder.encode(faculty.getPassword()));
        template.save(faculty, "faculties");
        return "Faculty "+faculty.getId()+" added successfully.";
    }
    public DeleteResult removeFaculty(Faculty faculty){
        return template.remove(faculty, "faculties");
    }
}
