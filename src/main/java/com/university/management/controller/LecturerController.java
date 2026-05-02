package com.university.management.controller;

import com.university.management.model.Lecturer;
import com.university.management.service.DepartmentService;
import com.university.management.service.LecturerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Managed by: Ramiru
 */
@Controller
@RequestMapping("/lecturers")
public class LecturerController {

    @Autowired private LecturerService lecturerService;
    @Autowired private DepartmentService departmentService;  // Needed to populate department dropdown

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInAdmin") != null;
    }

    @GetMapping
    public String listLecturers(Model model, HttpSession session,
                                @RequestParam(required = false) String search) {
        if (!isLoggedIn(session)) return "redirect:/login";

        if (search != null && !search.isEmpty()) {
            model.addAttribute("lecturers", lecturerService.search(search));
            model.addAttribute("searchTerm", search);
        } else {
            model.addAttribute("lecturers", lecturerService.findAll());
        }
        return "lecturer/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("lecturer", new Lecturer());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("isEdit", false);
        return "lecturer/form";
    }

    @PostMapping("/add")
    public String saveLecturer(@Valid @ModelAttribute Lecturer lecturer,
                               BindingResult result,
                               @RequestParam(required = false) Long departmentId,
                               Model model,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";

        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("isEdit", false);
            return "lecturer/form";
        }

        // Set the department from the dropdown selection
        if (departmentId != null) {
            departmentService.findById(departmentId).ifPresent(lecturer::setDepartment);
        }

        lecturerService.save(lecturer);
        redirectAttributes.addFlashAttribute("success", "Lecturer added successfully!");
        return "redirect:/lecturers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        Lecturer lecturer = lecturerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));
        model.addAttribute("lecturer", lecturer);
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("isEdit", true);
        return "lecturer/form";
    }

    @PostMapping("/edit/{id}")
    public String updateLecturer(@PathVariable Long id,
                                 @Valid @ModelAttribute Lecturer lecturer,
                                 BindingResult result,
                                 @RequestParam(required = false) Long departmentId,
                                 Model model,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("isEdit", true);
            return "lecturer/form";
        }
        lecturer.setId(id);
        if (departmentId != null) {
            departmentService.findById(departmentId).ifPresent(lecturer::setDepartment);
        }
        lecturerService.save(lecturer);
        redirectAttributes.addFlashAttribute("success", "Lecturer updated successfully!");
        return "redirect:/lecturers";
    }

    @GetMapping("/delete/{id}")
    public String deleteLecturer(@PathVariable Long id, HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        try {
            lecturerService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Lecturer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete lecturer — they are assigned to courses!");
        }
        return "redirect:/lecturers";
    }
}