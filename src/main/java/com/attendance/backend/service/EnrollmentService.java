package com.attendance.backend.service;

import com.attendance.backend.SortStudentsById;
import com.attendance.backend.model.Course;
import com.attendance.backend.model.Student;
import com.attendance.backend.model.Enrollment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    @Autowired
    private MongoTemplate template;
    public List<Course> getCoursesByStudent(Long studentId){
        Student student = template.findById(studentId, Student.class, "students");
        return template.query(Enrollment.class)
                .matching(Criteria.where("student").is(student))
                .stream()
                .map(Enrollment::getCourse)
                .toList();
    }

    public List<Course> getOtherCoursesByStudent(Long studentId){
        List<Course> allCourses = template.findAll(Course.class, "courses");
        allCourses.removeAll(getCoursesByStudent(studentId));
        return allCourses;
    }

    public List<Student> getStudentsByCourse(String code){
        Course course = template.findById(code, Course.class, "courses");
        return template.query(Enrollment.class)
                .matching(Criteria.where("course").is(course))
                .stream()
                .map(Enrollment::getStudent)
                .sorted((p1,p2) -> p1.getId().compareTo(p2.getId()))
                .toList();
    }

    public Enrollment addStudentCourse(Long studentId, String code){
        Student student = template.findById(studentId, Student.class, "students");
        Course course = template.findById(code, Course.class, "courses");
        Query query = new Query(Criteria.where("student").is(student).and("course").is(course));
        Enrollment enrollment = template.findOne(query,Enrollment.class,"enrollment");
        if(enrollment==null)
            enrollment = template.save(new Enrollment(student,course), "enrollment");
        return enrollment;
    }

    //public
}
