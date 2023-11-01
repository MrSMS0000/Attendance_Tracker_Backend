package com.attendance.backend.tleaf_controllers;

import com.attendance.backend.model.Course;
import com.attendance.backend.model.Student;
import com.attendance.backend.security.PersonToUserDetails;
import com.attendance.backend.service.AttendanceService;
import com.attendance.backend.service.CourseService;
import com.attendance.backend.service.EnrollmentService;
import com.attendance.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/student")
@PreAuthorize("hasAuthority('ROLE_STUDENT')")
public class TStudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private CourseService courseService;
    @GetMapping("/home")
    public String homePage(Model model, @AuthenticationPrincipal PersonToUserDetails person){
        Student student = studentService.getStudent(Long.parseLong(person.getUsername()));
        model.addAttribute("student",student);
        return "student/home";
    }

    @GetMapping("/gate")
    public String gateAttendance(Model model, @AuthenticationPrincipal PersonToUserDetails person){
        Student student = studentService.getStudent(Long.parseLong(person.getUsername()));
        model.addAttribute("student",student);
        return "student/gate";
    }

    @GetMapping("/view")
    public String viewCourses(Model model, @AuthenticationPrincipal PersonToUserDetails person) {
        Student student = studentService.getStudent(Long.parseLong(person.getUsername()));
        model.addAttribute("student",student);
        List<Course> courses = enrollmentService.getCoursesByStudent(Long.parseLong(person.getUsername()));
        model.addAttribute("courses", courses);
        return "student/view";
    }

    @GetMapping("/class/{code}")
    public String classAttendance(@AuthenticationPrincipal PersonToUserDetails person, @PathVariable String code, Model model){
        Long id = Long.parseLong(person.getUsername());
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

    @PostMapping("/mark/{id}/{code}")
    public String markAttendance(@PathVariable Long id, @PathVariable String code, Model model, RedirectAttributes redirectAttributes){
        String response = attendanceService.markStudentAttendance(id,code);
        redirectAttributes.addFlashAttribute("response",response);
        return "redirect:/student/view";
    }

    @GetMapping("/choose")
    public String chooseCourses(Model model, @AuthenticationPrincipal PersonToUserDetails person) {
        Student student = studentService.getStudent(Long.parseLong(person.getUsername()));
        model.addAttribute("student",student);
        List<Course> courses = enrollmentService.getOtherCoursesByStudent(Long.parseLong(person.getUsername()));
        model.addAttribute("courses", courses);
        return "student/choose";
    }

    @PostMapping("/add/{id}/{code}")
    public String addStudentCourse(@PathVariable Long id, @PathVariable String code){
        enrollmentService.addStudentCourse(id,code);
        return "redirect:/student/choose";
    }

}
