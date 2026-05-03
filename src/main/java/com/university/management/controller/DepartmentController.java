package com.university.management.controller;

import com.university.management.model.Department;
import com.university.management.service.DepartmentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * =====================================================
 * DEPARTMENT CONTROLLER
 * =====================================================
 * Managed by: Chathuranga
 *
 * URL Mapping:
 *   GET  /university/departments          → List all departments
 *   GET  /university/departments/add      → Show add form
 *   POST /university/departments/add      → Save new department
 *   GET  /university/departments/edit/{id}→ Show edit form
 *   POST /university/departments/edit/{id}→ Update department
 *   GET  /university/departments/delete/{id} → Delete department
 *   GET  /university/departments/search   → Search departments
 * =====================================================
 */
@Controller
@RequestMapping("/departments")  // All URLs in this controller start with /departments
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // ---- Helper: Check if admin is logged in ----
    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInAdmin") != null;
    }

    // ================================================
    // READ — List all departments
    // ================================================
    @GetMapping
    public String listDepartments(Model model, HttpSession session,
                                  @RequestParam(required = false) String search) {
        if (!isLoggedIn(session)) return "redirect:/login";

        if (search != null && !search.isEmpty()) {
            model.addAttribute("departments", departmentService.search(search));
            model.addAttribute("searchTerm", search);
        } else {
            model.addAttribute("departments", departmentService.findAll());
        }
        return "department/list";  // → WEB-INF/views/department/list.jsp
    }

    // ================================================
    // CREATE — Show add form
    // ================================================
    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("department", new Department());  // Empty form object
        model.addAttribute("isEdit", false);
        return "department/form";
    }

    // ================================================
    // CREATE — Process add form submission
    // ================================================
    @PostMapping("/add")
    public String saveDepartment(@Valid @ModelAttribute Department department,
                                 BindingResult result,
                                 Model model,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";

        // @Valid triggers validation annotations on Department model
        // BindingResult holds any validation errors
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "department/form";  // Show form again with error messages
        }

        // Check for duplicate code
        if (departmentService.existsByCode(department.getCode())) {
            model.addAttribute("error", "Department code '" + department.getCode() + "' already exists!");
            model.addAttribute("isEdit", false);
            return "department/form";
        }

        departmentService.save(department);
        redirectAttributes.addFlashAttribute("success", "Department '" + department.getName() + "' added successfully!");
        return "redirect:/departments";
    }

    // ================================================
    // UPDATE — Show edit form
    // ================================================
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";

        Department department = departmentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));

        model.addAttribute("department", department);
        model.addAttribute("isEdit", true);
        return "department/form";
    }

    // ================================================
    // UPDATE — Process edit form
    // ================================================
    @PostMapping("/edit/{id}")
    public String updateDepartment(@PathVariable Long id,
                                   @Valid @ModelAttribute Department department,
                                   BindingResult result,
                                   Model model,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";

        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "department/form";
        }

        department.setId(id);  // Make sure the right record gets updated
        departmentService.save(department);
        redirectAttributes.addFlashAttribute("success", "Department updated successfully!");
        return "redirect:/departments";
    }

    // ================================================
    // DELETE — Remove department
    // ================================================
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";

        try {
            departmentService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Department deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete department — it has associated lecturers or courses!");
        }
        return "redirect:/departments";
    }
}