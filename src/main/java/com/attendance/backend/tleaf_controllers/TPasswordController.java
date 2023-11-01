package com.attendance.backend.tleaf_controllers;

import com.attendance.backend.model.Person;
import com.attendance.backend.security.PersonToUserDetails;
import com.attendance.backend.service.FacultyService;
import com.attendance.backend.service.PasswordService;
import com.attendance.backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/password")
public class TPasswordController {
    @Autowired
    private PasswordService passwordService;
    @GetMapping("/change")
    public String changePasswordPage(Model model, @AuthenticationPrincipal PersonToUserDetails person) {
        model.addAttribute("person", person);
        return "password/change";
    }
    @PostMapping("/change")
    public String changePassword(
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model,
            @AuthenticationPrincipal PersonToUserDetails person) {

        Long id = Long.parseLong(person.getUsername()); // Get the username

        if (newPassword.equals(confirmPassword)) {
            passwordService.changePassword(id, newPassword);
            model.addAttribute("message", "Password changed successfully");
        } else {
            model.addAttribute("message", "New password and confirm password do not match");
        }

        model.addAttribute("person", person);
        return "password/change";
    }
}
