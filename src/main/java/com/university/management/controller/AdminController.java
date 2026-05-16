package com.abcinstitute.student_management.controller;

import com.abcinstitute.student_management.model.*;
import com.abcinstitute.student_management.repository.*;
import com.abcinstitute.student_management.service.AdminService;
import com.abcinstitute.student_management.service.CourseService;
import com.abcinstitute.student_management.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
/** Managed by: Ramanan */
@Controller
@RequestMapping("/admins")
public class AdminController {

    @Autowired private CourseService courseService;
    @Autowired private StudentService studentService;
    @Autowired private AdminService adminService;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private LectureRepository lectureRepository;
    @Autowired private EnrollmentRepository enrollmentRepository;

    // ─── Guard: check admin session ───
    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("userType"));
    }
     // ─── Guard: check super-admin ───
    private boolean isSuperAdmin(HttpSession session) {
        Admin admin = (Admin) session.getAttribute("loggedInAdmin");
        return "SUPER_ADMIN".equals(session.getAttribute("adminRole"));
    }

    /** Injects adminRole into the model so all templates can conditionally render the Admins nav link */
    private void addCommonAttributes(HttpSession session, Model model) {
        model.addAttribute("adminRole", session.getAttribute("adminRole"));
        model.addAttribute("adminName", session.getAttribute("loggedInUser"));
    }

    // Admin Dashboard
    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/admin-login";
        model.addAttribute("totalCourses", courseService.getAllCourses().size());
        model.addAttribute("totalStudents", studentService.getAllStudents().size());
        model.addAttribute("totalDepts", departmentRepository.count());
        model.addAttribute("totalLecturers", lectureRepository.count());
        addCommonAttributes(session, model);
        return "admin/admin-dashboard";
    }

    // ─────────────── COURSES ───────────────
    @GetMapping("/courses")
    public String listCourses(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/admin-login";
        model.addAttribute("courses", courseService.getAllCourses());
        addCommonAttributes(session, model);
        return "admin/courses";
    }

    @GetMapping("/courses/add")
    public String addCourseForm(HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/admin-login";
        model.addAttribute("course", new Course());
        model.addAttribute("departments", departmentRepository.findAll());
        model.addAttribute("lecturers", lectureRepository.findAll());
        addCommonAttributes(session, model);
        return "admin/add-course";
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

    ///// MAPPING...TO ADD DEPARTMENT
    @GetMapping("/departments/add")
    public String addDeptForm(HttpSession session) {
        if (!isAdmin(session)) return "redirect:/admin-login";
        return "admin/add-department";   // points to templates/admin/add-department.html
    }


    /// // delete student method
    @GetMapping("/students/delete/{username}")
    public String deleteStudent(@PathVariable String username, HttpSession session, RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/admin-login";

        boolean deleted = studentService.deleteStudent(username);
        if (deleted) {
            ra.addFlashAttribute("success", "Student '" + username + "' has been removed.");
        } else {
            ra.addFlashAttribute("error", "Unable to delete student. They may not exist.");
        }
        return "redirect:/admin/students";
    }

   ////////// EDIT STUDENT METHODS
    // GET: Show edit student form
    @GetMapping("/students/edit/{username}")
    public String editStudentForm(@PathVariable String username, HttpSession session, Model model) {
        if (!isAdmin(session)) return "redirect:/admin-login";

        Student student = studentService.findByUsername(username).orElse(null);
        if (student == null) return "redirect:/admin/students";

        model.addAttribute("student", student);
        return "admin/edit-student";
    }

    // POST: Process student edit
    @PostMapping("/students/edit/{username}")
    public String updateStudent(@PathVariable String username,
                                @RequestParam String fullName,
                                @RequestParam String email,
                                @RequestParam String phone,
                                @RequestParam(required = false) String newPassword,
                                @RequestParam(required = false) String confirmPassword,
                                HttpSession session,
                                RedirectAttributes ra) {
        if (!isAdmin(session)) return "redirect:/admin-login";

        Student student = studentService.findByUsername(username).orElse(null);
        if (student == null) return "redirect:/admin/students";

        // Update non-sensitive fields
        student.setFullName(fullName);
        student.setEmail(email);
        student.setPhone(phone);

        // Update password only if provided and matching
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            if (!newPassword.equals(confirmPassword)) {
                ra.addFlashAttribute("error", "Passwords do not match!");
                return "redirect:/admin/students/edit/" + username;
            }
            student.setPassword(newPassword);
        }

        studentService.updateStudent(student);
        ra.addFlashAttribute("success", "Student '" + username + "' updated successfully.");
        return "redirect:/admin/students";
    }

}
