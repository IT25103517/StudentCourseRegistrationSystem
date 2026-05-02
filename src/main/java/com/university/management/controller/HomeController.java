package com.university.management.controller;

import com.university.management.model.Admin;
import com.university.management.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * =====================================================
 * HOME CONTROLLER
 * =====================================================
 * Handles:
 *  - Main dashboard (/)
 *  - Login page (/login)
 *  - Logout (/logout)
 *
 * WHAT IS A CONTROLLER?
 * A Controller receives HTTP requests from the browser,
 * calls the appropriate service, and returns a JSP view name.
 * Think of it as a traffic policeman.
 *
 * Flow: Browser → Controller → Service → Repository → MySQL
 *                           ← ← ← ← ← ← ← ← ← ← ←
 * =====================================================
 */
@Controller  // Tells Spring: "This class handles web requests"
public class HomeController {

    // Spring injects all these service instances automatically
    @Autowired private DepartmentService departmentService;
    @Autowired private LecturerService lecturerService;
    @Autowired private StudentService studentService;
    @Autowired private CourseService courseService;
    @Autowired private EnrollmentService enrollmentService;
    @Autowired private AdminService adminService;

    /**
     * GET /university/ → Show dashboard
     * Model = a container to pass data from Java to the JSP page.
     */
    @GetMapping("/")
    public String dashboard(Model model, HttpSession session) {
        // Check if admin is logged in
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";  // Not logged in → redirect to login page
        }

        // Add statistics to model — these appear on the dashboard cards
        model.addAttribute("departmentCount", departmentService.count());
        model.addAttribute("lecturerCount", lecturerService.count());
        model.addAttribute("studentCount", studentService.count());
        model.addAttribute("courseCount", courseService.count());
        model.addAttribute("enrollmentCount", enrollmentService.count());

        // Add recent data
        model.addAttribute("recentStudents", studentService.findAll().stream().limit(5).toList());
        model.addAttribute("recentCourses", courseService.findAll().stream().limit(5).toList());

        return "index";  // Returns src/main/webapp/WEB-INF/views/index.jsp
    }

    /**
     * GET /university/login → Show login page
     */
    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        if (session.getAttribute("loggedInAdmin") != null) {
            return "redirect:/";  // Already logged in → go to dashboard
        }
        return "login";
    }

    /**
     * POST /university/login → Process login form submission
     */
    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        // Try to find admin with matching username and password
        var adminOptional = adminService.authenticate(username, password);

        if (adminOptional.isPresent()) {
            // Login success — save admin in session
            session.setAttribute("loggedInAdmin", adminOptional.get());
            session.setAttribute("adminUsername", adminOptional.get().getUsername());
            return "redirect:/";
        } else {
            // Login failed — show error message
            redirectAttributes.addFlashAttribute("error", "Invalid username or password!");
            return "redirect:/login";
        }
    }

    /**
     * GET /university/logout → Log out and clear session
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // Destroy the session
        return "redirect:/login";
    }
}