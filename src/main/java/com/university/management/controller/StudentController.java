package com.university.management.controller;

import com.university.management.model.Student;
import com.university.management.service.StudentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Managed by: Navodya */
@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired private StudentService studentService;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInAdmin") != null;
    }

    @GetMapping
    public String listStudents(Model model, HttpSession session,
                               @RequestParam(required = false) String search) {
        if (!isLoggedIn(session)) return "redirect:/login";
        if (search != null && !search.isEmpty()) {
            model.addAttribute("students", studentService.search(search));
            model.addAttribute("searchTerm", search);
        } else {
            model.addAttribute("students", studentService.findAll());
        }
        return "student/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("student", new Student());
        model.addAttribute("isEdit", false);
        model.addAttribute("statuses", Student.Status.values());
        return "student/form";
    }

    @PostMapping("/add")
    public String saveStudent(@Valid @ModelAttribute Student student,
                              BindingResult result, Model model,
                              HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            model.addAttribute("statuses", Student.Status.values());
            return "student/form";
        }
        studentService.save(student);
        redirectAttributes.addFlashAttribute("success", "Student registered successfully!");
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("student", studentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found")));
        model.addAttribute("isEdit", true);
        model.addAttribute("statuses", Student.Status.values());
        return "student/form";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @Valid @ModelAttribute Student student,
                                BindingResult result, Model model,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            model.addAttribute("statuses", Student.Status.values());
            return "student/form";
        }
        student.setId(id);
        studentService.save(student);
        redirectAttributes.addFlashAttribute("success", "Student updated successfully!");
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        try {
            studentService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Student deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete student — they have active enrollments!");
        }
        return "redirect:/students";
    }
}