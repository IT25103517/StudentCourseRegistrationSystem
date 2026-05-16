package com.university.management.controller;

import com.university.management.model.Admin;
import com.university.management.service.AdminService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/** Managed by: Ramanan */
@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired private AdminService adminService;

    private boolean isLoggedIn(HttpSession session) {
        return session.getAttribute("loggedInAdmin") != null;
    }

    private boolean isSuperAdmin(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");
        return admin != null && admin.getRole() == Admin.Role.SUPER_ADMIN;
    }

    @GetMapping
    public String listAdmins(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("admins", adminService.findAll());
        model.addAttribute("roles", Admin.Role.values());
        return "admin/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("admin", new Admin());
        model.addAttribute("roles", Admin.Role.values());
        model.addAttribute("isEdit", false);
        return "admin/form";
    }

    @PostMapping("/add")
    public String saveAdmin(@Valid @ModelAttribute Admin admin,
                            BindingResult result, Model model,
                            HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("roles", Admin.Role.values());
            model.addAttribute("isEdit", false);
            return "admin/form";
        }
        adminService.save(admin);
        redirectAttributes.addFlashAttribute("success", "Admin account created successfully!");
        return "redirect:/admins";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!isLoggedIn(session)) return "redirect:/login";
        model.addAttribute("admin", adminService.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found")));
        model.addAttribute("roles", Admin.Role.values());
        model.addAttribute("isEdit", true);
        return "admin/form";
    }

    @PostMapping("/edit/{id}")
    public String updateAdmin(@PathVariable Long id,
                              @Valid @ModelAttribute Admin admin,
                              BindingResult result, Model model,
                              HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn(session)) return "redirect:/login";
        if (result.hasErrors()) {
            model.addAttribute("roles", Admin.Role.values());
            model.addAttribute("isEdit", true);
            return "admin/form";
        }
        admin.setId(id);
        adminService.save(admin);
        redirectAttributes.addFlashAttribute("success", "Admin updated successfully!");
        return "redirect:/admins";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdmin(@PathVariable Long id, HttpSession session,
                              RedirectAttributes redirectAttributes) {
        if (!isSuperAdmin(session)) {
            redirectAttributes.addFlashAttribute("error", "Only Super Admin can delete admin accounts!");
            return "redirect:/admins";
        }
        try {
            adminService.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Admin deleted!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting admin!");
        }
        return "redirect:/admins";
    }
}

// ─────────────── EDIT LECTURER ───────────────
    @GetMapping("/lectures/edit/{id}")
    public String editLecturerForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/admin-login";

        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/admin/lectures";

        model.addAttribute("lecture", lecture);
        model.addAttribute("departments", departmentRepository.findAll());
        return "admin/edit-lecture";
    }

    @PostMapping("/lectures/edit/{id}")
    public String updateLecturer(@PathVariable Long id,
                                 @RequestParam String lecturerName,
                                 @RequestParam String email,
                                 @RequestParam String phone,
                                 @RequestParam Long departmentId,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/admin-login";

        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) return "redirect:/admin/lectures";

        lecture.setLecturerName(lecturerName);
        lecture.setEmail(email);
        lecture.setPhone(phone);

        departmentRepository.findById(departmentId).ifPresent(lecture::setDepartment);
        lectureRepository.save(lecture);

        ra.addFlashAttribute("success", "Lecturer updated successfully.");
        return "redirect:/admin/lectures";
    }

// GET: Show edit department form
    @GetMapping("/departments/edit/{id}")
    public String editDeptForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/admin-login";

        Department dept = departmentRepository.findById(id).orElse(null);
        if (dept == null) return "redirect:/admin/departments";

        model.addAttribute("department", dept);
        return "admin/edit-department";
    }

    /// //////////// EDIT DEPARTMENT BUTTON
    //////////////// POST: Process department update

    @PostMapping("/departments/edit/{id}")
    public String updateDept(@PathVariable Long id,
                             @RequestParam String deptName,
                             @RequestParam String deptCode,
                             @RequestParam String description,
                             @RequestParam String headOfDept,
                             HttpSession session,
                             RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/admin-login";

        Department dept = departmentRepository.findById(id).orElse(null);
        if (dept == null) return "redirect:/admin/departments";

        dept.setDeptName(deptName);
        dept.setDeptCode(deptCode);
        dept.setDescription(description);
        dept.setHeadOfDept(headOfDept);

        departmentRepository.save(dept);
        ra.addFlashAttribute("success", "Department updated successfully.");
        return "redirect:/admin/departments";
    }
