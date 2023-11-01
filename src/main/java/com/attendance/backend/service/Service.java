package com.attendance.backend.service;

import com.attendance.backend.model.Course;
import com.attendance.backend.model.Faculty;
import com.attendance.backend.model.GateEntry;
import com.attendance.backend.model.Student;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
@org.springframework.stereotype.Service
public class Service {
    @Autowired
    private MongoTemplate template;

    // returns null if student is not found.
    public List<GateEntry> getStudentGateAttendanceHistory(Long studentId){
        Student student = template.findById(studentId, Student.class, "students");
        if(student==null){
            return null;
        }
        return student.getGateAttendanceHistory();
    }

    // FUNCTIONALITIES
    public void addStudentGateAttendance(Long studentId, GateEntry newGateEntry){
         template.updateFirst(Query.query(Criteria.where("_id").is(studentId)),
                new Update().push("gateAttendanceHistory", newGateEntry),
                Student.class, "students");
    }

//    public List<Course> getStudentCourses(Long studentId){
//        return template.findById(studentId, Student.class, "students")
//                .getCourses()
//                .stream()
//                .map(courseCode -> template.findById(courseCode, Course.class, "courses"))
//                .toList();
//    }
//
//    public List<Course> getFacultyCourses(Long facultyId){
//        return template.findById(facultyId, Faculty.class, "faculties")
//                .getCourses()
//                .stream()
//                .map(courseCode -> template.findById(courseCode, Course.class, "courses"))
//                .toList();
//    }

    public void addCourseForStudent(Long studentId, String courseCode){
        // same course mate logic.
        template.updateFirst(Query.query(Criteria.where("_id").is(studentId)),
                new Update().push("courses", courseCode),
                Student.class,"students");
        template.updateFirst(Query.query(Criteria.where("_id").is(courseCode)),
                new Update().push("students", studentId),
                Course.class,"courses");
    }

//    public void addCourseForFaculty(Long facultyId, String courseCode){
//        // same course mate logic.
//        template.updateFirst(Query.query(Criteria.where("_id").is(facultyId)),
//                new Update().push("courses", courseCode),
//                Faculty.class,"faculties");
//
//        Course course = template.findById(courseCode, Course.class, "courses");
//        course.setFacultyId(facultyId);
//        template.save(course);
//    }

//    public List<Student> getStudentsForCourse(String courseCode){
//        return template.findById(courseCode, Course.class, "courses")
//                .getStudents()
//                .stream()
//                .map(studentId -> template.findById(studentId, Student.class, "students"))
//                .toList();
//    }

    // GET IT
    public Student getStudent(Long studentId){
        return template.findById(studentId, Student.class, "students");
    }

    public Faculty getFaculty(Long facultyId){
        return template.findById(facultyId, Faculty.class, "faculties");
    }

    public Course getCourse(String courseCode){
        return template.findById(courseCode, Course.class, "courses");
    }

    // ADD IT
    public Student addStudent(Student student){
        return template.save(student,"students");
    }

    public Faculty addFaculty(Faculty faculty){
        return template.save(faculty, "faculties");
    }

    public Course addCourse(Course course){
        return template.save(course, "courses");
    }
}
