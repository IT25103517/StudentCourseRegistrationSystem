package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =====================================================
 * STUDENT ENTITY — Extends Person
 * =====================================================
 * Managed by: Navodya
 *
 * OOP CONCEPTS:
 * - INHERITANCE: BaseEntity → Person → Student
 *   Inherits: id, timestamps, firstName, lastName, email, phone
 *   Adds:     studentId, address, dateOfBirth, status, enrollments
 *
 * - ENCAPSULATION: Private fields with getters/setters
 * - POLYMORPHISM: getFullName() can show student ID with name
 *
 * DATABASE TABLE: students
 * =====================================================
 */
@Entity
@Table(name = "students")
public class Student extends Person {

    /**
     * Unique student registration number like "STU2024001"
     */
    @NotBlank(message = "Student ID is required")
    @Column(name = "student_id", unique = true, nullable = false, length = 20)
    private String studentId;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * Student's password for login (in real apps, this would be hashed)
     */
    @Column(name = "password", length = 255)
    private String password;

    /**
     * Enrollment status: ACTIVE, INACTIVE, GRADUATED, SUSPENDED
     */
    @Enumerated(EnumType.STRING)  // Stores enum as string ("ACTIVE") not number (0)
    @Column(name = "status", length = 20)
    private Status status = Status.ACTIVE;  // Default = ACTIVE

    /**
     * POLYMORPHISM EXAMPLE — Enum with different display behavior:
     * Each status has its own display label and Bootstrap CSS class.
     */
    public enum Status {
        ACTIVE("Active", "success"),
        INACTIVE("Inactive", "secondary"),
        GRADUATED("Graduated", "primary"),
        SUSPENDED("Suspended", "danger");

        private final String displayName;
        private final String badgeClass;  // Bootstrap color class

        // Enum constructor
        Status(String displayName, String badgeClass) {
            this.displayName = displayName;
            this.badgeClass = badgeClass;
        }

        public String getDisplayName() { return displayName; }
        public String getBadgeClass() { return badgeClass; }
    }

    /**
     * ONE Student can have MANY Enrollments.
     * This is the student's record of all courses they've enrolled in.
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    // ---- Constructors ----

    public Student() {}

    // ---- Getters and Setters ----

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void setEnrollments(List<Enrollment> enrollments) { this.enrollments = enrollments; }

    /**
     * POLYMORPHISM: Override getFullName() from Person
     * Student's full name includes their student ID in brackets.
     */
    @Override
    public String getFullName() {
        return super.getFullName() + " [" + studentId + "]";
    }

    /**
     * Helper to get name without ID (useful in views).
     */
    public String getDisplayName() {
        return super.getFullName();
    }
}