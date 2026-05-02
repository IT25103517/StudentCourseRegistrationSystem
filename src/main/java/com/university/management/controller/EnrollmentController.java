package com.university.management.controller;

import com.university.management.model.Enrollment;
import com.university.management.service.CourseService;
import com.university.management.service.EnrollmentService;
import com.university.management.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Managed by: De Silva */
@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    @Autowired private EnrollmentService enrollmentService;
    @Autowired private StudentService studentService;
    @Autowired private CourseService courseService;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInAdmin") != null;
    }

    @GetMapping
    public String listEnrollments(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("enrollments", enrollmentService.findAll());
        return "enrollment/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("enrollment", new Enrollment());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("statuses", Enrollment.EnrollmentStatus.values());
        model.addAttribute("isEdit", false);
        return "enrollment/form";
    }

    @PostMapping("/add")
    public String saveEnrollment(@RequestParam Long studentId,
                                 @RequestParam Long courseId,
                                 @RequestParam(required = false) String grade,
                                 @RequestParam(required = false) Double marks,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";

        // Prevent duplicate enrollment
        if (enrollmentService.isStudentEnrolled(studentId, courseId)) {
            redirectAttributes.addFlashAttribute("error", "This student is already enrolled in this course!");
            return "redirect:/enrollments/add";
        }

        Enrollment enrollment = new Enrollment();
        studentService.findById(studentId).ifPresent(enrollment::setStudent);
        courseService.findById(courseId).ifPresent(enrollment::setCourse);
        if (grade != null && !grade.isEmpty()) enrollment.setGrade(grade);
        if (marks != null) enrollment.setMarks(marks);

        enrollmentService.save(enrollment);
        redirectAttributes.addFlashAttribute("success", "Student enrolled successfully!");
        return "redirect:/enrollments";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("enrollment", enrollmentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found")));
        model.addAttribute("statuses", Enrollment.EnrollmentStatus.values());
        model.addAttribute("isEdit", true);
        return "enrollment/form";
    }

    @PostMapping("/edit/{id}")
    public String updateEnrollment(@PathVariable Long id,
                                   @RequestParam Enrollment.EnrollmentStatus status,
                                   @RequestParam(required = false) String grade,
                                   @RequestParam(required = false) Double marks,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        enrollmentService.findById(id).ifPresent(enrollment -> {
            enrollment.setStatus(status);
            if (grade != null && !grade.isEmpty()) enrollment.setGrade(grade);
            if (marks != null) enrollment.setMarks(marks);
            enrollmentService.save(enrollment);
        });
        redirectAttributes.addFlashAttribute("success", "Enrollment updated successfully!");
        return "redirect:/enrollments";
    }

    @GetMapping("/delete/{id}")
    public String deleteEnrollment(@PathVariable Long id, HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        enrollmentService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Enrollment deleted successfully!");
        return "redirect:/enrollments";
    }
}