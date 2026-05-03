package com.university.management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * =====================================================
 * LECTURER ENTITY — Extends Person
 * =====================================================
 * Managed by: Ramiru
 *
 * OOP CONCEPTS:
 * - INHERITANCE: Extends Person (which extends BaseEntity)
 *   FULL CHAIN: BaseEntity → Person → Lecturer
 *   Lecturer INHERITS: id, createdAt, updatedAt (from BaseEntity)
 *                      firstName, lastName, email, phone (from Person)
 *   Lecturer ADDS:     employeeId, qualification, designation, department
 *
 * - ENCAPSULATION: Private fields, public getters/setters
 * - POLYMORPHISM: getFullName() inherited from Person, can override
 *
 * DATABASE TABLE: lecturers
 * =====================================================
 */
@Entity
@Table(name = "lecturers")
public class Lecturer extends Person {

    /**
     * Unique lecturer ID like "LEC001"
     */
    @NotBlank(message = "Employee ID is required")
    @Column(name = "employee_id", unique = true, length = 20)
    private String employeeId;

    /**
     * Highest degree: e.g., "PhD in Computer Science", "MSc Engineering"
     */
    @Column(name = "qualification", length = 200)
    private String qualification;

    /**
     * Job title: "Senior Lecturer", "Associate Professor", "Professor"
     */
    @Column(name = "designation", length = 100)
    private String designation;

    /**
     * Which specialization/subject area they teach
     */
    @Column(name = "specialization", length = 150)
    private String specialization;

    /**
     * MANY Lecturers belong to ONE Department.
     * @ManyToOne creates a foreign key in the lecturers table (department_id).
     * @JoinColumn specifies the foreign key column name.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // ---- Constructors ----

    public Lecturer() {}  // Required by JPA

    // ---- Getters and Setters (Encapsulation) ----

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) { this.qualification = qualification; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }

    /**
     * POLYMORPHISM EXAMPLE:
     * Overriding getFullName() from Person class to include designation.
     * Same method name, DIFFERENT behavior in this subclass.
     */
    @Override
    public String getFullName() {
        // Calling super.getFullName() gets "John Smith" from Person class
        // We extend it to include the designation: "Prof. John Smith"
        String parentFullName = super.getFullName();
        if (designation != null && !designation.isEmpty()) {
            return designation + " " + parentFullName;
        }
        return parentFullName;
    }

    @Override
    public String toString() {
        return "Lecturer{id=" + getId() + ", name=" + getFullName() + ", employeeId=" + employeeId + "}";
    }
}