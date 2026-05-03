package com.university.management.controller;

import com.university.management.model.Course;
import com.university.management.service.CourseService;
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

/** Managed by: Ashen */
@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired private CourseService courseService;
    @Autowired private DepartmentService departmentService;
    @Autowired private LecturerService lecturerService;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInAdmin") != null;
    }

    @GetMapping
    public String listCourses(Model model, HttpSession session,
                              @RequestParam(required = false) String search) {
        if (!isLoggedIn(session)) return "redirect:/login";
        if (search != null && !search.isEmpty()) {
            model.addAttribute("courses", courseService.search(search));
            model.addAttribute("searchTerm", search);
        } else {
            model.addAttribute("courses", courseService.findAll());
        }
        return "course/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("course", new Course());
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("lecturers", lecturerService.findAll());
        model.addAttribute("isEdit", false);
        return "course/form";
    }

    @PostMapping("/add")
    public String saveCourse(@Valid @ModelAttribute Course course,
                             BindingResult result,
                             @RequestParam(required = false) Long departmentId,
                             @RequestParam(required = false) Long lecturerId,
                             Model model, HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("lecturers", lecturerService.findAll());
            model.addAttribute("isEdit", false);
            return "course/form";
        }
        if (departmentId != null) departmentService.findById(departmentId).ifPresent(course::setDepartment);
        if (lecturerId != null) lecturerService.findById(lecturerId).ifPresent(course::setLecturer);

        courseService.save(course);
        redirectAttributes.addFlashAttribute("success", "Course added successfully!");
        return "redirect:/courses";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("course", courseService.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found")));
        model.addAttribute("departments", departmentService.findAll());
        model.addAttribute("lecturers", lecturerService.findAll());
        model.addAttribute("isEdit", true);
        return "course/form";
    }

    @PostMapping("/edit/{id}")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute Course course,
                               BindingResult result,
                               @RequestParam(required = false) Long departmentId,
                               @RequestParam(required = false) Long lecturerId,
                               Model model, HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            model.addAttribute("lecturers", lecturerService.findAll());
            model.addAttribute("isEdit", true);
            return "course/form";
        }
        course.setId(id);
        if (departmentId != null) departmentService.findById(departmentId).ifPresent(course::setDepartment);
        if (lecturerId != null) lecturerService.findById(lecturerId).ifPresent(course::setLecturer);
        courseService.save(course);
        redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
        return "redirect:/courses";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Long id, HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        try {
            courseService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete course — students are enrolled!");
        }
        return "redirect:/courses";
    }
}