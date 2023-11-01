package com.attendance.backend.service;

import com.attendance.backend.model.Course;
import com.attendance.backend.model.Faculty;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private MongoTemplate template;
    public List<Course> getCourses(){
        return template.findAll(Course.class,"courses");
    }
    public Course getCourse(String code){
        return template.findById(code, Course.class, "courses");
    }
    public String addCourse(Course course){
        if(template.findById(course.getCode(), Course.class, "courses")!=null){
            return "Course "+course.getCode()+" already exist.";
        }
        template.save(course, "courses");
        return "Course "+course.getCode()+" added successfully.";
    }
    public DeleteResult removeCourse(Course course){
        return template.remove(course, "courses");
    }

    public List<Course> getCoursesByFaculty(Long id){
        Faculty faculty = template.findById(id, Faculty.class, "faculties");
        return template.query(Course.class)
                .matching(Criteria.where("faculty").is(faculty))
                .all();
    }

    public List<Course> getOtherCoursesByFaculty(Long id){
        Faculty faculty = template.findById(id, Faculty.class, "faculties");
        return template.query(Course.class)
                .matching(Criteria.where("faculty").isNull())
                .all();
    }

    public Course addFacultyCourse(Long id, String code) {
        Faculty faculty = template.findById(id, Faculty.class, "faculties");
        Course course = template.findById(code, Course.class, "courses");
        course.setFaculty(faculty);
        return template.save(course);
    }
}
