package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * =====================================================
 * DEPARTMENT ENTITY
 * =====================================================
 * Represents the 'departments' table in MySQL.
 * Managed by: Chathuranga
 *
 * OOP CONCEPTS:
 * - ENCAPSULATION: All fields private, access via getters/setters
 * - INHERITANCE: Extends BaseEntity (gets id, createdAt, updatedAt)
 *
 * DATABASE TABLE: departments
 * =====================================================
 */
@Entity  // Tells JPA: "Create a MySQL table for this class"
@Table(name = "departments")  // The table will be named "departments"
public class Department extends BaseEntity {

    @NotBlank(message = "Department name is required")
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    /**
     * Department code like "CS", "ENG", "BUS"
     */
    @NotBlank(message = "Department code is required")
    @Column(name = "code", unique = true, length = 10)
    private String code;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "head_of_department", length = 150)
    private String headOfDepartment;

    /**
     * ONE Department has MANY Lecturers.
     * mappedBy = "department" links to the 'department' field in Lecturer class.
     * cascade = PERSIST means when we save a Department, related Lecturers also save.
     * fetch = LAZY means Lecturers are only loaded from DB when we access this list.
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lecturer> lecturers = new ArrayList<>();

    /**
     * ONE Department has MANY Courses.
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();

    // ---- Constructors ----

    public Department() {} // Default constructor required by JPA

    public Department(String name, String code, String description, String headOfDepartment) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.headOfDepartment = headOfDepartment;
    }

    // ---- Getters and Setters (Encapsulation) ----

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHeadOfDepartment() { return headOfDepartment; }
    public void setHeadOfDepartment(String headOfDepartment) { this.headOfDepartment = headOfDepartment; }

    public List<Lecturer> getLecturers() { return lecturers; }
    public void setLecturers(List<Lecturer> lecturers) { this.lecturers = lecturers; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

    /**
     * Returns number of lecturers in this department.
     * Useful for the dashboard display.
     */
    public int getLecturerCount() {
        return lecturers != null ? lecturers.size() : 0;
    }

    @Override
    public String toString() {
        return "Department{id=" + getId() + ", name=" + name + ", code=" + code + "}";
    }
}