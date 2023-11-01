package com.attendance.backend.service;

import com.attendance.backend.model.Faculty;
import com.attendance.backend.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    @Autowired
    private MongoTemplate template;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void changePassword(Long id, String newPassword){
        Student student = template.findById(id, Student.class, "students");
        if(student != null){
            student.setPassword(passwordEncoder.encode(newPassword));
            template.save(student,"students");
        }
        else{
            Faculty faculty = template.findById(id, Faculty.class, "faculties");
            if(faculty!=null) {
                faculty.setPassword(passwordEncoder.encode(newPassword));
                template.save(faculty, "faculties");
            }
        }
    }
}
