package com.attendance.backend.security;

import com.attendance.backend.model.Person;
import com.attendance.backend.service.FacultyService;
import com.attendance.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class PersonToUserDetailsService implements UserDetailsService {

    @Autowired
    private StudentService studentService;
    @Autowired
    private FacultyService facultyService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = studentService.getStudent(Long.parseLong(username));
        if(person==null) {
            person = facultyService.getFaculty(Long.parseLong(username));
        }
        if(person==null){
            throw new UsernameNotFoundException("User does not exist!");
        }
        return new PersonToUserDetails(person);
    }
}
