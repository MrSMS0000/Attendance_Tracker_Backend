package com.attendance.backend.service;

import com.attendance.backend.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private MongoTemplate template;
    public String markStudentAttendance(Long studentId, String courseCode){
        LocalDate date = LocalDate.now();
        Student student = template.findById(studentId, Student.class, "students");
        Course course = template.findById(courseCode, Course.class, "courses");
        if(!template.exists(Query.query(Criteria.where("course").is(course).and("date").is(date)), CourseAttendance.class, "current_attendance"))
            return "Not accepting attendance right now in course " + courseCode;
        Query query = new Query(Criteria.where("student").is(student).and("course").is(course).and("date").is(date));
        StudentAttendance oldAttendance = template.findOne(query, StudentAttendance.class, "student_attendance");
        if(oldAttendance==null) {
            template.save(new StudentAttendance(student, course, date));
            return "Attendance is marked for date " + date + " in course " + courseCode;
        }
        return "Already marked attendance for date " + date + " in " + courseCode;
    }

    public List<LocalDate> getStudentAttendance(Long studentId, String courseCode){
        Student student = template.findById(studentId, Student.class, "students");
        Course course = template.findById(courseCode, Course.class, "courses");
        Query query = new Query(Criteria.where("student").is(student).and("course").is(course));
        return template.find(query,StudentAttendance.class,"student_attendance")
                .stream().map(StudentAttendance::getDate).toList();
    }

    public String startCourseAttendance(String courseCode){
        LocalDate date = LocalDate.now();
        Course course = template.findById(courseCode, Course.class, "courses");
        Query courseQuery = new Query(Criteria.where("course").is(course).and("date").is(date));
        CourseAttendance oldCourseAttendance = template.findOne(courseQuery, CourseAttendance.class, "course_attendance");
        Query currentQuery = new Query(Criteria.where("course").is(course));
        CourseAttendance oldCurrentAttendance = template.findOne(currentQuery, CourseAttendance.class, "current_attendance");
        if(oldCourseAttendance==null){
            CourseAttendance newAttendance = new CourseAttendance(course,date);
            template.save(newAttendance,"current_attendance");
            template.save(newAttendance,"course_attendance");
        } else if (oldCurrentAttendance==null) {
            CourseAttendance newAttendance = new CourseAttendance(course,date);
            template.save(newAttendance,"current_attendance");
        }
        else {
            return "Attendance in course " + courseCode + " is already started.";
        }
        return "Attendance is started in course " + courseCode + " for date " +  date;
    }

    public String  stopCourseAttendance(String courseCode){
        Course course = template.findById(courseCode, Course.class, "courses");
        Query query = new Query(Criteria.where("course").is(course));
        CourseAttendance courseAttendance = template.findAndRemove(query, CourseAttendance.class, "current_attendance");
        if(courseAttendance == null){
            return "Attendance in course " + courseCode + " is not started yet!";
        }
        return "Attendance is stopped in course " + courseCode + " for date " +  courseAttendance.getDate();
    }

    public String checkIfStarted(String courseCode){
        Course course = template.findById(courseCode, Course.class, "courses");
        Query query = new Query(Criteria.where("course").is(course));
        if(template.exists(query,CourseAttendance.class,"current_attendance")){
            return "stop";
        }
        return "start";
    }
    public List<LocalDate> getCourseAttendance(String courseCode){
        Course course = template.findById(courseCode, Course.class, "courses");
        Query query = new Query(Criteria.where("course").is(course));
        return template.find(query,StudentAttendance.class,"course_attendance")
                .stream().map(StudentAttendance::getDate).toList();
    }
}

