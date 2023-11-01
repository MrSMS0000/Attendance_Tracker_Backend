package com.attendance.backend.tleaf_controllers;

import com.attendance.backend.model.Course;
import com.attendance.backend.model.Faculty;
import com.attendance.backend.model.Student;
import com.attendance.backend.service.CourseService;
import com.attendance.backend.service.FacultyService;
import com.attendance.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class TAdminController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private CourseService courseService;
    private boolean check(Long id){
        if(studentService.getStudent(id)!=null)
            return true;
        return facultyService.getFaculty(id) != null;
    }
    @GetMapping("/home")
    public String homePage(){
        return "admin/home";
    }

    @GetMapping("/add/faculty")
    public String addFaculty(Model model){
        model.addAttribute("faculty", new Faculty());
        return "admin/addFaculty";
    }

    @PostMapping("/add/faculty")
    public String addFaculty(@ModelAttribute Faculty faculty, RedirectAttributes redirectAttributes){
        String result = facultyService.addFaculty(faculty);
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/admin/add/faculty";
    }
    @GetMapping("/add/course")
    public String addCourse(Model model){
        model.addAttribute("course", new Course());
        return "admin/addCourse";
    }

    @PostMapping("/add/course")
    public String addCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes){
        String result = courseService.addCourse(course);
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/admin/add/course";
    }

    @GetMapping("/add/student")
    public String addStudentForm(Model model){
        model.addAttribute("student", new Student());
        return "admin/addStudent";
    }

    @PostMapping("/add/student")
    public String addStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes){
        String result = studentService.addStudent(student);
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/admin/add/student";
    }
}
