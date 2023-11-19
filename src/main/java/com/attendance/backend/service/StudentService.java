package com.attendance.backend.service;

import com.attendance.backend.model.Student;
import com.attendance.backend.model.Student;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public List<Student> getStudents(){
        return template.findAll(Student.class,"students");
    }
    public Student getStudent(Long id){
        return template.findById(id, Student.class, "students");
    }
    public String addStudent(Student student){
        if(template.findById(student.getId(), Student.class, "students")!=null){
            return "Id "+student.getId()+" already exist.";
        }
        student.setRoles("ROLE_STUDENT");
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        template.save(student, "students");
        return "Student "+student.getId()+" added successfully.";
    }
    public DeleteResult removeStudent(Student student){
        return template.remove(student, "students");
    }
}
