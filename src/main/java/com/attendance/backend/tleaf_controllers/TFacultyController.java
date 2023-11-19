package com.attendance.backend.tleaf_controllers;

import com.attendance.backend.SortStudentsById;
import com.attendance.backend.model.*;
import com.attendance.backend.model.Faculty;
import com.attendance.backend.security.PersonToUserDetails;
import com.attendance.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/faculty")
@PreAuthorize("hasAuthority('ROLE_FACULTY')")
public class TFacultyController {

    @Autowired
    private FacultyService facultyService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AttendanceService attendanceService;
    @GetMapping("/home")
    public String homePage(Model model, @AuthenticationPrincipal PersonToUserDetails person) {
        Faculty faculty = facultyService.getFaculty(Long.parseLong(person.getUsername()));
        model.addAttribute("faculty", faculty);
        return "faculty/home";
    }

    @GetMapping("/course")
    public String FacultyCourses(Model model, @AuthenticationPrincipal PersonToUserDetails person){
        Faculty faculty = facultyService.getFaculty(Long.parseLong(person.getUsername()));
        model.addAttribute("faculty",faculty);
        List<Course> courses = courseService.getCoursesByFaculty(Long.parseLong(person.getUsername()));
        model.addAttribute("courses", courses);
        List<Course> otherCourses = courseService.getOtherCoursesByFaculty(Long.parseLong(person.getUsername()));
        model.addAttribute("otherCourses", otherCourses);
        return "faculty/course";
    }

    @PostMapping("/add/{id}/{code}")
    public String addFacultyCourse(@PathVariable Long id, @PathVariable String code){
        courseService.addFacultyCourse(id,code);
        return "redirect:/faculty/course";
    }


    @GetMapping("/class/{code}")
    public String getStudents(@PathVariable String code, Model model, @AuthenticationPrincipal PersonToUserDetails person){
        Faculty faculty = facultyService.getFaculty(Long.parseLong(person.getUsername()));
        model.addAttribute("faculty",faculty);
        Course course = courseService.getCourse(code);
        model.addAttribute("course", course);
        List<Student> studentList = enrollmentService.getStudentsByCourse(code);
        model.addAttribute("studentList",studentList);
        return "faculty/class";
    }

    @GetMapping("/attendance/{id}/{code}")
    public String getStudentsAttendance(@PathVariable Long id, @PathVariable String code, Model model){
        Student student = studentService.getStudent(id);
        model.addAttribute("student",student);
        Course course = courseService.getCourse(code);
        model.addAttribute("course", course);
        List<LocalDate> studentList = attendanceService.getStudentAttendance(id,code);
        List<LocalDate> courseList = attendanceService.getCourseAttendance(code);
        model.addAttribute("studentList",studentList);
        model.addAttribute("courseList",courseList);
        return "student/class";
    }

    @PostMapping("/start/{code}")
    public String startCourseAttendance(@PathVariable String code){
        attendanceService.startCourseAttendance(code);
        return "redirect:/faculty/course";
    }

    @PostMapping("/stop/{code}")
    public String stopCourseAttendance(@PathVariable String code){
        attendanceService.stopCourseAttendance(code);
        return "redirect:/faculty/course";
    }
}
